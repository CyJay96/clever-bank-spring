package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.UserMapper;
import ru.clevertec.cleverbank.model.dto.request.UserDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.UserDtoResponse;
import ru.clevertec.cleverbank.model.entity.User;
import ru.clevertec.cleverbank.repository.UserRepository;
import ru.clevertec.cleverbank.service.UserService;

import java.util.List;

/**
 * User Service to work with the User entity
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Save a new User entity
     *
     * @param userDtoRequest User DTO to save
     * @return saved User DTO
     */
    @Override
    public UserDtoResponse save(UserDtoRequest userDtoRequest) {
        User user = userMapper.toUser(userDtoRequest);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDtoResponse(savedUser);
    }

    /**
     * Find all User entities info
     *
     * @param pageable page number & page size values to find
     * @return all User DTOs
     */
    @Override
    public PageResponse<UserDtoResponse> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserDtoResponse> userDtoResponses = userPage.stream()
                .map(userMapper::toUserDtoResponse)
                .toList();

        return PageResponse.<UserDtoResponse>builder()
                .content(userDtoResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(userDtoResponses.size())
                .build();
    }

    /**
     * Find User entity info by ID. Uses the Redis-cache implementation
     *
     * @param id User ID to find
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     * @return found User DTO by ID
     */
    @Override
    public UserDtoResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDtoResponse)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    /**
     * Update an existing User entity info by ID
     *
     * @param id User ID to update
     * @param userDtoRequest User DTO to update
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     * @return updated User DTO by ID
     */
    @Override
    public UserDtoResponse update(Long id, UserDtoRequest userDtoRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
        userMapper.updateUser(userDtoRequest, user);
        return userMapper.toUserDtoResponse(userRepository.save(user));
    }

    /**
     * Delete an User entity by ID
     *
     * @param id User ID to delete
     * @throws EntityNotFoundException if the User entity with ID doesn't exist
     */
    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(User.class, id);
        }

        userRepository.deleteById(id);
    }
}
