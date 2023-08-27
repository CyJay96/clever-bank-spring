package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;

public interface TransactionService {

    TransactionDtoResponse replenishBalance(TransactionDtoRequest transactionDtoRequest);

    TransactionDtoResponse withdrawBalance(TransactionDtoRequest transactionDtoRequest);

    TransactionDtoResponse transferFunds(TransactionDtoRequest transactionDtoRequest);

    PageResponse<TransactionDtoResponse> findAll(Pageable pageable);

    TransactionDtoResponse findById(Long id);

    TransactionDtoResponse deleteById(Long id);
}
