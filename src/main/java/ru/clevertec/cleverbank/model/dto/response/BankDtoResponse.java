package ru.clevertec.cleverbank.model.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.cleverbank.model.enums.Status;

import java.io.Serializable;
import java.util.List;

/**
 * Bank DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class BankDtoResponse implements Serializable {

    private Long id;

    private String title;

    private List<AccountDtoResponse> accounts;

    private Status status;
}
