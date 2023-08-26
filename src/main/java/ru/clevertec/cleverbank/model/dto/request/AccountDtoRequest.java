package ru.clevertec.cleverbank.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Account DTO for requests
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class AccountDtoRequest implements Serializable {

    @NotBlank(message = "Number cannot be empty")
    @Length(max = 255, message = "Number is too long")
    @JsonProperty(value = "number")
    private String number;

    @NotNull(message = "User ID cannot be null")
    @PositiveOrZero(message = "User ID must be positive or zero")
    @JsonProperty(value = "userId")
    private Long userId;

    @NotNull(message = "Bank ID cannot be null")
    @PositiveOrZero(message = "Bank ID must be positive or zero")
    @JsonProperty(value = "bankId")
    private Long bankId;
}
