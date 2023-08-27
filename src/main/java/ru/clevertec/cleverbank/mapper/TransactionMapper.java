package ru.clevertec.cleverbank.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;
import ru.clevertec.cleverbank.model.entity.Transaction;

/**
 * Mapper for Transaction entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

    @Mapping(target = "supplierId", expression = "java(transaction.getSupplier() != null ? transaction.getSupplier().getId() : null)")
    @Mapping(target = "consumerId", expression = "java(transaction.getConsumer() != null ? transaction.getConsumer().getId() : null)")
    TransactionDtoResponse toTransactionDtoResponse(Transaction transaction);
}
