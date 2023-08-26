package ru.clevertec.cleverbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.cleverbank.model.dto.request.TransactionDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.TransactionDtoResponse;
import ru.clevertec.cleverbank.model.entity.Transaction;

/**
 * Mapper for Transaction entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    Transaction toTransaction(TransactionDtoRequest transactionDtoRequest);

    TransactionDtoResponse toTransactionDtoResponse(Transaction transaction);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    void updateTransaction(TransactionDtoRequest transactionDtoRequest, @MappingTarget Transaction transaction);
}
