package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.TransactionMapper;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;
import ru.clevertec.cleverbank.model.entity.Account;
import ru.clevertec.cleverbank.model.entity.Transaction;
import ru.clevertec.cleverbank.model.enums.Status;
import ru.clevertec.cleverbank.model.enums.TransactionType;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.service.TransactionService;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Transaction Service to work with the Transaction entity
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    /**
     * Replenish balance an existing Account entity by ID
     *
     * @param transactionDtoRequest Transaction DTO to replenish balance
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return Account DTO by ID with replenished balance
     */
    @Override
    @Transactional
    public TransactionDtoResponse replenishBalance(TransactionDtoRequest transactionDtoRequest) {
        Account consumer = accountRepository.findById(transactionDtoRequest.getConsumerId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, transactionDtoRequest.getConsumerId()));

        consumer.setBalance(consumer.getBalance().add(transactionDtoRequest.getAmount()));

        accountRepository.save(consumer);

        Transaction transaction = Transaction.builder()
                .consumer(consumer)
                .amount(transactionDtoRequest.getAmount())
                .transactionType(TransactionType.REPLENISHMENT)
                .createDate(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Withdraw balance an existing Account entity by ID
     *
     * @param transactionDtoRequest Transaction DTO to withdraw balance
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return Account DTO by ID with withdrawn balance
     */
    @Override
    @Transactional
    public TransactionDtoResponse withdrawBalance(TransactionDtoRequest transactionDtoRequest) {
        Account supplier = accountRepository.findById(transactionDtoRequest.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, transactionDtoRequest.getSupplierId()));

        supplier.setBalance(supplier.getBalance().subtract(transactionDtoRequest.getAmount()));

        accountRepository.save(supplier);

        Transaction transaction = Transaction.builder()
                .supplier(supplier)
                .amount(transactionDtoRequest.getAmount())
                .transactionType(TransactionType.WITHDRAWAL)
                .createDate(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Transfer funds to another user by supplier and consumer accounts IDs
     *
     * @param transactionDtoRequest Transaction DTO to transfer funds
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return partial updated Transaction DTO by ID
     */
    @Override
    @Transactional
    public TransactionDtoResponse transferFunds(TransactionDtoRequest transactionDtoRequest) {
        Account supplier = accountRepository.findById(transactionDtoRequest.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, transactionDtoRequest.getSupplierId()));
        Account consumer = accountRepository.findById(transactionDtoRequest.getConsumerId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, transactionDtoRequest.getConsumerId()));

        supplier.setBalance(supplier.getBalance().subtract(transactionDtoRequest.getAmount()));
        consumer.setBalance(consumer.getBalance().add(transactionDtoRequest.getAmount()));

        accountRepository.save(supplier);
        accountRepository.save(consumer);

        Transaction transaction = Transaction.builder()
                .supplier(supplier)
                .consumer(consumer)
                .amount(transactionDtoRequest.getAmount())
                .transactionType(TransactionType.TRANSFER)
                .createDate(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Find all Transaction entities info
     *
     * @param pageable page number & page size values to find
     * @return all Transaction DTOs
     */
    @Override
    @Transactional
    public PageResponse<TransactionDtoResponse> findAll(Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        List<TransactionDtoResponse> transactionDtoResponses = transactionPage.stream()
                .map(transactionMapper::toTransactionDtoResponse)
                .toList();

        return PageResponse.<TransactionDtoResponse>builder()
                .content(transactionDtoResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(transactionDtoResponses.size())
                .build();
    }

    /**
     * Find Transaction entity info by ID. Uses the Redis-cache implementation
     *
     * @param id Transaction ID to find
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     * @return found Transaction DTO by ID
     */
    @Override
    @Transactional
    public TransactionDtoResponse findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toTransactionDtoResponse)
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, id));
    }

    /**
     * Delete a Transaction entity by ID
     *
     * @param id Transaction ID to delete
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     * @return deleted Transaction DTO by ID
     */
    @Override
    @Transactional
    public TransactionDtoResponse deleteById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, id));
        transaction.setStatus(Status.DELETED);
        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }
}
