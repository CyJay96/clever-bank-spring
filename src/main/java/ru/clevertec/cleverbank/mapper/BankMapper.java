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

/**
 * Mapper for Bank entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BankMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "status", ignore = true)
    Bank toBank(BankDtoRequest bankDtoRequest);

    @Mapping(target = "accountsIds", expression = "java(bank.getAccounts().stream().map(account -> account.getId()).toList())")
    BankDtoResponse toBankDtoResponse(Bank bank);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateBank(BankDtoRequest bankDtoRequest, @MappingTarget Bank bank);
}
