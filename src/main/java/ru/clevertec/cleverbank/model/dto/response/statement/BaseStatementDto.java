package ru.clevertec.cleverbank.model.dto.response.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Abstract money statement DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@SuperBuilder
public class BaseStatementDto {

    @JsonProperty(value = "bank")
    private String bank;

    @JsonProperty(value = "client")
    private String client;

    @JsonProperty(value = "account")
    private String account;

    @JsonProperty(value = "accountCreateDate")
    private String accountCreateDate;

    @JsonProperty(value = "period")
    private String period;

    @JsonProperty(value = "createDateTime")
    private String createDateTime;

    @JsonProperty(value = "balance")
    private String balance;
}
