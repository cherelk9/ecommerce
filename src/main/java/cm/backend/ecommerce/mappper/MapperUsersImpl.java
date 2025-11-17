package cm.backend.ecommerce.mappper;

import org.springframework.stereotype.Component;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.mappper.interfaces.MapperUsers;
import cm.backend.ecommerce.models.Users;
import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;

/**
 * private Long userId;
 * private String username;
 * private String email;
 * private String password;
 * private int age;
 * private Role role;
 * private Statut statut;
 */

@Component
public class MapperUsersImpl implements MapperUsers {

    @Override
    public Users toEntity(UsersRequestDto dto) {
        Long userId = new Users().getUserId();
        String password = new Users().getPassword();
        int age = new Users().getAge();
        Role role = new Users().getRole();
        Statut statut = new Users().getStatut();

        return Users.builder()
                .userId(userId)
                .username(dto.username())
                .email(dto.email())
                .password(password)
                .age(age)
                .role(role)
                .statut(statut)
                .build();
    }

    @Override
    public UsersRequestDto toDto(Users entity) {
        return UsersRequestDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public UsersResponseDto toResponseDto(Users entity) {
        return UsersResponseDto.builder()
                .userId(String.valueOf(entity.getUserId()))
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(entity.getRole())
                .statut(entity.getStatut())
                .build();
    }

    @Override
    public Users toEntity(UsersResponseDto dto) {
        String password = new Users().getPassword();
        int age = new Users().getAge();

        return Users.builder()
                .userId(Long.valueOf(dto.userId()))
                .username(dto.username())
                .email(dto.email())
                .password(password)
                .age(age)
                .role(dto.role())
                .statut(dto.statut())
                .build();
    }

}
