package ru.clevertec.cleverbank.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.dto.response.statement.StatementDto;
import ru.clevertec.cleverbank.model.enums.StatementPeriod;

public interface AccountService {

    AccountDtoResponse save(AccountDtoRequest accountDtoRequest);

    PageResponse<AccountDtoResponse> findAll(Pageable pageable);

    AccountDtoResponse findById(String id);

    StatementDto getStatementByAccountId(String id, StatementPeriod statementPeriod);

    AccountDtoResponse deleteById(String id);
}
