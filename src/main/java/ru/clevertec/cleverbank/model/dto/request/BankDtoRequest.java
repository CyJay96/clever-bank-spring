package ru.clevertec.cleverbank.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Bank DTO for requests
 *
 * @author Konstantin Voytko
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankDtoRequest implements Serializable {

    @Length(max = 255, message = "Title is too long")
    @NotBlank(message = "Title cannot be empty")
    private String title;
}
