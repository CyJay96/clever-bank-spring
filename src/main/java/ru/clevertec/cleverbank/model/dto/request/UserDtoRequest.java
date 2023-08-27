package ru.clevertec.cleverbank.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static ru.clevertec.cleverbank.util.Constants.EMAIL_REGEX;

/**
 * User DTO for requests
 *
 * @author Konstantin Voytko
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest implements Serializable {

    @NotBlank(message = "User first name cannot be empty")
    @Length(max = 255, message = "User first name is too long")
    @JsonProperty(value = "firstName")
    private String firstName;

    @NotBlank(message = "User last name cannot be empty")
    @Length(max = 255, message = "User last name is too long")
    @JsonProperty(value = "lastName")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Length(max = 255, message = "User email is too long")
    @Pattern(regexp = EMAIL_REGEX, message = "Incorrect email address")
    @JsonProperty(value = "email")
    private String email;
}
