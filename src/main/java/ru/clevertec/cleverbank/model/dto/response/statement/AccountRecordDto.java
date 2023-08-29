package ru.clevertec.cleverbank.model.dto.response.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Account record DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@SuperBuilder
public class AccountRecordDto extends BaseStatementDto implements Serializable {

    @JsonProperty(value = "transactions")
    private List<TransactionShortDto> transactions;
}
