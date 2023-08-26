package ru.clevertec.cleverbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.entity.Account;

/**
 * Mapper for Account entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "status", expression = "java(Status.ACTIVE)")
    Account toAccount(AccountDtoRequest accountDtoRequest);

    @Mapping(target = "userId", expression = "java(account.getUser().getId())")
    @Mapping(target = "bankId", expression = "java(account.getBank().getId())")
    AccountDtoResponse toAccountDtoResponse(Account account);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    void updateAccount(AccountDtoRequest accountDtoRequest, @MappingTarget Account account);
}
