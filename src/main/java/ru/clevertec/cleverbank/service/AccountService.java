package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.statement.AccountRecordDto;
import ru.clevertec.cleverbank.model.dto.response.statement.StatementDto;
import ru.clevertec.cleverbank.model.enums.StatementPeriod;

public interface AccountService {

    AccountRecordDto getAccountRecordById(String id, StatementPeriod statementPeriod);

    StatementDto getStatementById(String id);

    AccountDtoResponse save(AccountDtoRequest accountDtoRequest);

    PageResponse<AccountDtoResponse> findAll(Pageable pageable);

    AccountDtoResponse findById(String id);

    AccountDtoResponse deleteById(String id);
}
