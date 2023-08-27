package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;

public interface AccountService {

    AccountDtoResponse save(AccountDtoRequest accountDtoRequest);

    PageResponse<AccountDtoResponse> findAll(Pageable pageable);

    AccountDtoResponse findById(String id);

    AccountDtoResponse deleteById(String id);
}
