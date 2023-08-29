package ru.clevertec.cleverbank.model.dto.response.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Money statement DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@SuperBuilder
public class StatementDto extends BaseStatementDto implements Serializable {

    @JsonProperty(value = "replenishment")
    private String replenishment;

    @JsonProperty(value = "withdrawal")
    private String withdrawal;
}
