package ru.clevertec.cleverbank.model.dto.response.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Statement DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class StatementDto implements Serializable {

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

    @JsonProperty(value = "transactions")
    private List<TransactionShortDto> transactions;
}
