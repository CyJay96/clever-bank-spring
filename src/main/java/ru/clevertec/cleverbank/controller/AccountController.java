package ru.clevertec.cleverbank.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<AccountDtoResponse> findById(@PathVariable @NotNull String id) {
        AccountDtoResponse account = accountService.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * DELETE /api/v0/accounts/{id} : Delete an Account entity by ID
     *
     * @param id Account ID to delete (required)
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return deleted Account DTO by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> deleteById(@PathVariable @NotNull String id) {
        AccountDtoResponse account = accountService.deleteById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
