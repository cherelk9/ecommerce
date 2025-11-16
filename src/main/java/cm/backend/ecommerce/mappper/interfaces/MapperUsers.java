package cm.backend.ecommerce.mappper.interfaces;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.models.Users;

public interface MapperUsers {

    Users toEntity(UsersRequestDto dto);

    UsersRequestDto toDto(Users entity);

    UsersResponseDto toResponseDto(Users entity);

    Users toEntity(UsersResponseDto dto);

}
