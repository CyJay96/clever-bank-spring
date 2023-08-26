package ru.clevertec.cleverbank.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Bank DTO for requests
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class BankDtoRequest implements Serializable {

    @Length(max = 255, message = "Title is too long")
    @NotBlank(message = "Title cannot be empty")
    private String title;
}
