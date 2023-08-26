package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.TransactionMapper;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;
import ru.clevertec.cleverbank.model.entity.Transaction;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.service.TransactionService;

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
    private final TransactionMapper transactionMapper;

    /**
     * Save a new Transaction entity
     *
     * @param transactionDtoRequest Transaction DTO to save
     * @return saved Transaction DTO
     */
    @Override
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
    public TransactionDtoResponse update(Long id, TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, id));
        transactionMapper.updateTransaction(transactionDtoRequest, transaction);
        return transactionMapper.toTransactionDtoResponse(transactionRepository.save(transaction));
    }

    /**
     * Delete a Transaction entity by ID
     *
     * @param id Transaction ID to delete
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     */
    @Override
    public void deleteById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException(Transaction.class, id);
        }

        transactionRepository.deleteById(id);
    }
}
