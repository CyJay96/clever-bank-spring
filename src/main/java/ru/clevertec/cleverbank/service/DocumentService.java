package ru.clevertec.cleverbank.service;

import ru.clevertec.cleverbank.model.dto.response.statement.AccountRecordDto;
import ru.clevertec.cleverbank.model.dto.response.statement.CheckDto;
import ru.clevertec.cleverbank.model.dto.response.statement.StatementDto;

public interface DocumentService {

    void saveCheck(CheckDto checkDto);

    void saveAccountRecord(AccountRecordDto accountRecordDto);

    void saveStatement(StatementDto statementDto);
}
