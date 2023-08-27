package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.BankDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.BankDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;

public interface BankService {

    BankDtoResponse save(BankDtoRequest bankDtoRequest);

    PageResponse<BankDtoResponse> findAll(Pageable pageable);

    BankDtoResponse findById(Long id);

    BankDtoResponse update(Long id, BankDtoRequest bankDtoRequest);

    BankDtoResponse deleteById(Long id);
}
