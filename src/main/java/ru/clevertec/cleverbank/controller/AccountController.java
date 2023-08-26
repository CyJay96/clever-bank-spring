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
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.service.AccountService;

/**
 * Account API
 *
 * @author Konstantin Voytko
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AccountController.COMMENT_API_PATH)
public class AccountController {

    private final AccountService accountService;

    public static final String COMMENT_API_PATH = "/api/v0/accounts";

    /**
     * POST /api/v0/accounts : Save a new Account entity
     *
     * @param accountDtoRequest Account DTO to save (required)
     * @return saved Account DTO
     */
    @PostMapping
    public ResponseEntity<AccountDtoResponse> save(@RequestBody @Valid AccountDtoRequest accountDtoRequest) {
        AccountDtoResponse account = accountService.save(accountDtoRequest);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    /**
     * GET /api/v0/accounts : Find all Account entities info
     *
     * @param pageable page number & page size values to find (not required)
     * @return all Account DTOs
     */
    @GetMapping
    public ResponseEntity<PageResponse<AccountDtoResponse>> findAll(Pageable pageable) {
        PageResponse<AccountDtoResponse> accounts = accountService.findAll(pageable);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * GET /api/v0/accounts/{id} : Find Account entity info by ID
     *
     * @param id Account ID to find (required)
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return found Account DTO by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        AccountDtoResponse account = accountService.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * PUT /api/v0/accounts/{id} : Update an existing Account entity info by ID
     *
     * @param id Account ID to update (required)
     * @param accountDtoRequest Account DTO to update (required)
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return updated Account DTO by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid AccountDtoRequest accountDtoRequest
    ) {
        AccountDtoResponse account = accountService.update(id, accountDtoRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * PATCH /api/v0/accounts/{id} : Partial Update an existing Account entity info by ID
     *
     * @param id Account ID to partial update (required)
     * @param accountDtoRequest Account DTO to partial update (required)
     * @throws EntityNotFoundException if Account entity with ID doesn't exist
     * @return partial updated Account DTO by ID
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody AccountDtoRequest accountDtoRequest
    ) {
        AccountDtoResponse account = accountService.update(id, accountDtoRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * DELETE /api/v0/accounts/{id} : Delete an Account entity by ID
     *
     * @param id Account ID to delete (required)
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
