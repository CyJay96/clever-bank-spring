package ru.clevertec.cleverbank.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.cleverbank.model.enums.Status;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Account DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class AccountDtoResponse implements Serializable {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "balance")
    private BigDecimal balance;

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "bankId")
    private Long bankId;

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;

    @JsonProperty(value = "status")
    private Status status;
}
