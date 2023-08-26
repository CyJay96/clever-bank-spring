package ru.clevertec.cleverbank.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
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

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;
}
