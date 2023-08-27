package ru.clevertec.cleverbank.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "createDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    Account toAccount(AccountDtoRequest accountDtoRequest);

    @Mapping(target = "userId", expression = "java(account.getUser().getId())")
    @Mapping(target = "bankId", expression = "java(account.getBank().getId())")
    @Mapping(target = "consumersIds", expression = "java(account.getConsumers().stream().map(consumer -> consumer.getId()).toList())")
    @Mapping(target = "suppliersIds", expression = "java(account.getSuppliers().stream().map(supplier -> supplier.getId()).toList())")
    AccountDtoResponse toAccountDtoResponse(Account account);
}
