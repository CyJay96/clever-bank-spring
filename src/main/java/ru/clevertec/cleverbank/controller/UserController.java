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
import ru.clevertec.cleverbank.model.dto.request.UserDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.UserDtoResponse;
import ru.clevertec.cleverbank.service.UserService;

/**
 * User API
 *
 * @author Konstantin Voytko
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserController.COMMENT_API_PATH)
public class UserController {

    private final UserService userService;

    public static final String COMMENT_API_PATH = "/api/v0/users";

    /**
     * POST /api/v0/users : Save a new User entity
     *
     * @param userDtoRequest User DTO to save (required)
     * @return saved User DTO
     */
    @PostMapping
    public ResponseEntity<UserDtoResponse> save(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        UserDtoResponse user = userService.save(userDtoRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * GET /api/v0/users : Find all User entities info
     *
     * @param pageable page number & page size values to find (not required)
     * @return all User DTOs
     */
    @GetMapping
    public ResponseEntity<PageResponse<UserDtoResponse>> findAll(Pageable pageable) {
        PageResponse<UserDtoResponse> users = userService.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * GET /api/v0/users/{id} : Find User entity info by ID
     *
     * @param id User ID to find (required)
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     * @return found User DTO by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        UserDtoResponse user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * PUT /api/v0/users/{id} : Update an existing User entity info by ID
     *
     * @param id User ID to update (required)
     * @param userDtoRequest User DTO to update (required)
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     * @return updated User DTO by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid UserDtoRequest userDtoRequest
    ) {
        UserDtoResponse user = userService.update(id, userDtoRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * PATCH /api/v0/users/{id} : Partial Update an existing User entity info by ID
     *
     * @param id User ID to partial update (required)
     * @param userDtoRequest User DTO to partial update (required)
     * @throws EntityNotFoundException if User entity with ID doesn't exist
     * @return partial updated User DTO by ID
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody UserDtoRequest userDtoRequest
    ) {
        UserDtoResponse user = userService.update(id, userDtoRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * DELETE /api/v0/users/{id} : Delete an User entity by ID
     *
     * @param id User ID to delete (required)
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     * @return deleted User DTO by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDtoResponse> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        UserDtoResponse user = userService.deleteById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
