package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;

public interface TransactionService {

    TransactionDtoResponse save(TransactionDtoRequest transactionDtoRequest);

    PageResponse<TransactionDtoResponse> findAll(Pageable pageable);

    TransactionDtoResponse findById(Long id);

    TransactionDtoResponse update(Long id, TransactionDtoRequest transactionDtoRequest);

    void deleteById(Long id);
}
