package cm.backend.ecommerce.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.exceptions.UserNotFoundException;
import cm.backend.ecommerce.mappper.interfaces.MapperUsers;
import cm.backend.ecommerce.models.Users;
import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import cm.backend.ecommerce.repositories.UsersRepository;
import cm.backend.ecommerce.services.interfaces.IUsersService;
import cm.backend.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;

/** 
 * private Long userId;
    private String username;
    private String email;
    private String password;
    private int age;
    private Role role;
    private Statut statut;

    for user entity
*/

/**
 * private username
 * private email
 * 
 * for user request 
*/

/**
 * String userId,
 * String username,
 * String email,
 * Role role,
 * Statut statut
 */

@Service
@RequiredArgsConstructor
public class UsersServiceImp implements IUsersService {

    private final UsersRepository usersRepository;
    private final MapperUsers mapperUsers;

    @Override
    public UsersResponseDto createUser(UsersRequestDto dto) {

        var user = mapperUsers.toEntity(dto);
        /**
         * user.setAge(new Users().getAge());
         * user.setPassword(new Users().getPassword());
         * user.setRole(new Users().getRole());
         * user.setStatut(new Users().getStatut());
         */

        user.setRole(Role.CUSTOMER);
        user.setStatut(Statut.ACTIVE);

        var userSaved = usersRepository.save(user);
        return mapperUsers.toResponseDto(userSaved);
    }

    @Override
    public UsersResponseDto getUserById(Long userId) {
        var user = usersRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserUtils.USER_NOT_FOUND + userId));
        return mapperUsers.toResponseDto(user);
    }

    @Override
    public Set<UsersResponseDto> getAllUsers() {
        var users = usersRepository.findAll();
        return users.stream()
                .map(mapperUsers::toResponseDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public UsersResponseDto updateUser(Long userId, UsersRequestDto dto) {
        /*
         * var existingUser = usersRepository.findById(userId)
         * .orElseThrow(() -> new UserNotFoundException(UserUtils.USER_NOT_FOUND
         * +userId));
         * 
         * existingUser.setUsername(dto.username());
         * existingUser.setEmail(dto.email());
         * // Update other fields as necessary
         * 
         * var updatedUser = usersRepository.save(existingUser);
         */
        // return mapperUsers.toResponseDto(updatedUser);

        return usersRepository.findById(userId)
                .map(user -> {
                    user.setUsername(dto.username());
                    user.setEmail(dto.email());
                    Users updatedUser = usersRepository.save(user);
                    return mapperUsers.toResponseDto(updatedUser);
                })
                .orElseThrow(() -> new UserNotFoundException(UserUtils.USER_NOT_FOUND + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        var user = usersRepository.findById((userId))
                .orElseThrow(() -> new UserNotFoundException(UserUtils.USER_NOT_FOUND + userId));
        usersRepository.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

}
