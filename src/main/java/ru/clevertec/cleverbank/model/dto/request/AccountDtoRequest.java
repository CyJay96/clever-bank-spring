package ru.clevertec.cleverbank.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Account DTO for requests
 *
 * @author Konstantin Voytko
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoRequest implements Serializable {

    @NotNull(message = "User ID cannot be null")
    @PositiveOrZero(message = "User ID must be positive or zero")
    @JsonProperty(value = "userId")
    private Long userId;

    @NotNull(message = "Bank ID cannot be null")
    @PositiveOrZero(message = "Bank ID must be positive or zero")
    @JsonProperty(value = "bankId")
    private Long bankId;
}
