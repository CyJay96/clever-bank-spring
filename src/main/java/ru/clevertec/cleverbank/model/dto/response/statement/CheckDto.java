package ru.clevertec.cleverbank.model.dto.response.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Check DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class CheckDto implements Serializable {

    @JsonProperty(value = "date")
    private String date;

    @JsonProperty(value = "time")
    private String time;

    @JsonProperty(value = "transactionType")
    private String transactionType;

    @JsonProperty(value = "supplierBank")
    private String supplierBank;

    @JsonProperty(value = "consumerBank")
    private String consumerBank;

    @JsonProperty(value = "supplierAccount")
    private String supplierAccount;

    @JsonProperty(value = "consumerAccount")
    private String consumerAccount;

    @JsonProperty(value = "amount")
    private String amount;
}
