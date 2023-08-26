package ru.clevertec.cleverbank.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;
import ru.clevertec.cleverbank.service.TransactionService;

/**
 * Transaction API
 *
 * @author Konstantin Voytko
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TransactionController.COMMENT_API_PATH)
public class TransactionController {

    private final TransactionService transactionService;

    public static final String COMMENT_API_PATH = "/api/v0/transactions";

    /**
     * POST /api/v0/transactions : Save a new Transaction entity
     *
     * @param transactionDtoRequest Transaction DTO to save (required)
     * @return saved Transaction DTO
     */
    @PostMapping
    public ResponseEntity<TransactionDtoResponse> save(@RequestBody @Valid TransactionDtoRequest transactionDtoRequest) {
        TransactionDtoResponse transaction = transactionService.save(transactionDtoRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    /**
     * GET /api/v0/transactions : Find all Transaction entities info
     *
     * @param pageable page number & page size values to find (not required)
     * @return all Transaction DTOs
     */
    @GetMapping
    public ResponseEntity<PageResponse<TransactionDtoResponse>> findAll(Pageable pageable) {
        PageResponse<TransactionDtoResponse> transactions = transactionService.findAll(pageable);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * GET /api/v0/transactions/{id} : Find Transaction entity info by ID
     *
     * @param id Transaction ID to find (required)
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     * @return found Transaction DTO by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDtoResponse> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        TransactionDtoResponse transaction = transactionService.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * PUT /api/v0/transactions/{id} : Update an existing Transaction entity info by ID
     *
     * @param id Transaction ID to update (required)
     * @param transactionDtoRequest Transaction DTO to update (required)
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     * @return updated Transaction DTO by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDtoResponse> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid TransactionDtoRequest transactionDtoRequest
    ) {
        TransactionDtoResponse transaction = transactionService.update(id, transactionDtoRequest);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * PATCH /api/v0/transactions/{id} : Partial Update an existing Transaction entity info by ID
     *
     * @param id Transaction ID to partial update (required)
     * @param transactionDtoRequest Transaction DTO to partial update (required)
     * @throws EntityNotFoundException if Transaction entity with ID doesn't exist
     * @return partial updated Transaction DTO by ID
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TransactionDtoResponse> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody TransactionDtoRequest transactionDtoRequest
    ) {
        TransactionDtoResponse transaction = transactionService.update(id, transactionDtoRequest);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * DELETE /api/v0/transactions/{id} : Delete a Transaction entity by ID
     *
     * @param id Transaction ID to delete (required)
     * @throws EntityNotFoundException if the Transaction entity with ID doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        transactionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
