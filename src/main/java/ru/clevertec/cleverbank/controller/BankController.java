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
import ru.clevertec.cleverbank.model.dto.request.BankDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.BankDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.service.BankService;

/**
 * Bank API
 *
 * @author Konstantin Voytko
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = BankController.COMMENT_API_PATH)
public class BankController {

    private final BankService bankService;

    public static final String COMMENT_API_PATH = "/api/v0/banks";

    /**
     * POST /api/v0/banks : Save a new Bank entity
     *
     * @param bankDtoRequest Bank DTO to save (required)
     * @return saved Bank DTO
     */
    @PostMapping
    public ResponseEntity<BankDtoResponse> save(@RequestBody @Valid BankDtoRequest bankDtoRequest) {
        BankDtoResponse bank = bankService.save(bankDtoRequest);
        return new ResponseEntity<>(bank, HttpStatus.CREATED);
    }

    /**
     * GET /api/v0/banks : Find all Bank entities info
     *
     * @param pageable page number & page size values to find (not required)
     * @return all Bank DTOs
     */
    @GetMapping
    public ResponseEntity<PageResponse<BankDtoResponse>> findAll(Pageable pageable) {
        PageResponse<BankDtoResponse> banks = bankService.findAll(pageable);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    /**
     * GET /api/v0/banks/{id} : Find Bank entity info by ID
     *
     * @param id Bank ID to find (required)
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return found Bank DTO by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankDtoResponse> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        BankDtoResponse bank = bankService.findById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    /**
     * PUT /api/v0/banks/{id} : Update an existing Bank entity info by ID
     *
     * @param id Bank ID to update (required)
     * @param bankDtoRequest Bank DTO to update (required)
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return updated Bank DTO by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankDtoResponse> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid BankDtoRequest bankDtoRequest
    ) {
        BankDtoResponse bank = bankService.update(id, bankDtoRequest);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    /**
     * PATCH /api/v0/banks/{id} : Partial Update an existing Bank entity info by ID
     *
     * @param id Bank ID to partial update (required)
     * @param bankDtoRequest Bank DTO to partial update (required)
     * @throws EntityNotFoundException if Bank entity with ID doesn't exist
     * @return partial updated Bank DTO by ID
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BankDtoResponse> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody BankDtoRequest bankDtoRequest
    ) {
        BankDtoResponse bank = bankService.update(id, bankDtoRequest);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    /**
     * DELETE /api/v0/banks/{id} : Delete a Bank entity by ID
     *
     * @param id Bank ID to delete (required)
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return deleted Bank DTO by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BankDtoResponse> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        BankDtoResponse bank = bankService.deleteById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }
}
