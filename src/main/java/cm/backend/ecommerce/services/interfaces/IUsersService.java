package cm.backend.ecommerce.services.interfaces;

import java.util.Set;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;

public interface IUsersService {

    public UsersResponseDto createUser(UsersRequestDto dto);

    public UsersResponseDto getUserById(Long userId);

    public Set<UsersResponseDto> getAllUsers();

    public UsersResponseDto updateUser(Long userId, UsersRequestDto dto);

    public void deleteUser(Long userId);

}
