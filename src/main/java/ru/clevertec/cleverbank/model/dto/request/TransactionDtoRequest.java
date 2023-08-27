package ru.clevertec.cleverbank.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Transaction DTO for requests
 *
 * @author Konstantin Voytko
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtoRequest implements Serializable {

    @Length(max = 255, message = "Supplier ID is too long")
    @JsonProperty(value = "supplierId")
    private String supplierId;

    @Length(max = 255, message = "Consumer ID is too long")
    @JsonProperty(value = "consumerId")
    private String consumerId;

    @NotNull(message = "Amount cannot be null")
    @PositiveOrZero(message = "Amount must be positive or zero")
    @JsonProperty(value = "amount")
    private BigDecimal amount;
}
