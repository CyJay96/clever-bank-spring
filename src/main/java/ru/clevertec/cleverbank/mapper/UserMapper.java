package ru.clevertec.cleverbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.cleverbank.model.dto.request.UserDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.UserDtoResponse;
import ru.clevertec.cleverbank.model.entity.User;
import ru.clevertec.cleverbank.model.enums.Status;

/**
 * Mapper for User entities & DTOs
 *
 * @author Konstantin Voytko
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "status", ignore = true)
    User toUser(UserDtoRequest userDtoRequest);

    UserDtoResponse toUserDtoResponse(User user);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastUpdateDate", expression = "java(java.time.OffsetDateTime.now())")
    void updateUser(UserDtoRequest userDtoRequest, @MappingTarget User user);
}
