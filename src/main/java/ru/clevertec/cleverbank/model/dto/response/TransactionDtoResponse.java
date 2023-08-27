package ru.clevertec.cleverbank.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.cleverbank.model.enums.Status;
import ru.clevertec.cleverbank.model.enums.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Transaction DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class TransactionDtoResponse implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "supplierId")
    private String supplierId;

    @JsonProperty(value = "consumerId")
    private String consumerId;

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "transactionType")
    private TransactionType transactionType;

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;

    @JsonProperty(value = "status")
    private Status status;
}
