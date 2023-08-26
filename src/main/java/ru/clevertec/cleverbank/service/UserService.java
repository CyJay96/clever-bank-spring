package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.UserDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.UserDtoResponse;

public interface UserService {

    UserDtoResponse save(UserDtoRequest userDtoRequest);

    PageResponse<UserDtoResponse> findAll(Pageable pageable);

    UserDtoResponse findById(Long id);

    UserDtoResponse update(Long id, UserDtoRequest userDtoRequest);

    void deleteById(Long id);
}
