package ru.clevertec.cleverbank.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.cleverbank.model.enums.Status;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * User DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class UserDtoResponse implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "accounts")
    private List<AccountDtoResponse> accounts;

    @JsonProperty(value = "createDate")
    private OffsetDateTime createDate;

    @JsonProperty(value = "lastUpdateDate")
    private OffsetDateTime lastUpdateDate;

    @JsonProperty(value = "status")
    private Status status;
}
