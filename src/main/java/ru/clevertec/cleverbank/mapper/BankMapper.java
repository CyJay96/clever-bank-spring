package ru.clevertec.cleverbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.cleverbank.model.dto.request.BankDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.BankDtoResponse;
import ru.clevertec.cleverbank.model.entity.Bank;
import ru.clevertec.cleverbank.model.enums.Status;

/**
 * Mapper for Bank entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(
        uses = {Status.class, AccountMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BankMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "status", expression = "java(Status.ACTIVE)")
    Bank toBank(BankDtoRequest bankDtoRequest);

    BankDtoResponse toBankDtoResponse(Bank bank);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    void updateBank(BankDtoRequest bankDtoRequest, @MappingTarget Bank bank);
}
