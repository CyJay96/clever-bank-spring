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
import ru.clevertec.cleverbank.model.enums.TransactionType;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.service.TransactionService;

import java.math.BigDecimal;
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
     * Save a new Transaction entity
     *
     * @param transactionDtoRequest Transaction DTO to save
     * @return saved Transaction DTO
     */
    @Override
    @Transactional
    public TransactionDtoResponse save(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction = transactionMapper.toTransaction(transactionDtoRequest);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionDtoResponse(savedTransaction);
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
     * Update an existing Transaction entity info by ID
     *
     * @param id Transaction ID to update
     * @param transactionDtoRequest Transaction DTO to update
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     * @return updated Transaction DTO by ID
     */
    @Override
    @Transactional
    public TransactionDtoResponse update(Long id, TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, id));
        transactionMapper.updateTransaction(transactionDtoRequest, transaction);
        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Replenish balance an existing Account entity by ID
     *
     * @param accountId Account ID to replenish balance
     * @param amount Amount of funds to replenish balance
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return Account DTO by ID with replenished balance
     */
    @Override
    @Transactional
    public TransactionDtoResponse replenishBalance(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, accountId));

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .consumer(account)
                .amount(amount)
                .transactionType(TransactionType.REPLENISHMENT)
                .createDate(OffsetDateTime.now())
                .lastUpdateDate(OffsetDateTime.now())
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Withdraw balance an existing Account entity by ID
     *
     * @param accountId Account ID to withdraw balance
     * @param amount Amount of funds to withdraw balance
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return Account DTO by ID with withdrawn balance
     */
    @Override
    @Transactional
    public TransactionDtoResponse withdrawBalance(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, accountId));

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .supplier(account)
                .amount(amount)
                .transactionType(TransactionType.WITHDRAWAL)
                .createDate(OffsetDateTime.now())
                .lastUpdateDate(OffsetDateTime.now())
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Transfer funds to another user by supplier and consumer accounts IDs
     *
     * @param supplierId Supplier ID to transfer funds
     * @param consumerId Consumer ID to transfer funds
     * @param amount Amount of funds to transfer
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return partial updated Transaction DTO by ID
     */
    @Override
    @Transactional
    public TransactionDtoResponse transferFunds(String supplierId, String consumerId, BigDecimal amount) {
        Account supplier = accountRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, supplierId));
        Account consumer = accountRepository.findById(consumerId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, consumerId));

        supplier.setBalance(supplier.getBalance().subtract(amount));
        consumer.setBalance(consumer.getBalance().add(amount));

        accountRepository.save(supplier);
        accountRepository.save(consumer);

        Transaction transaction = Transaction.builder()
                .supplier(supplier)
                .consumer(consumer)
                .amount(amount)
                .transactionType(TransactionType.TRANSFER)
                .createDate(OffsetDateTime.now())
                .lastUpdateDate(OffsetDateTime.now())
                .build();

        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Delete a Transaction entity by ID
     *
     * @param id Transaction ID to delete
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException(Transaction.class, id);
        }

        transactionRepository.deleteById(id);
    }
}
