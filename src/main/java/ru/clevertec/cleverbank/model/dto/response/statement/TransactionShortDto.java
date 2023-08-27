package ru.clevertec.cleverbank.model.dto.response.statement;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Transaction short DTO for responses
 *
 * @author Konstantin Voytko
 */
@Data
@Builder
public class TransactionShortDto implements Serializable {

    private String date;

    private String type;

    private String amount;
}
