package cm.backend.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.mappper.interfaces.MapperUsers;
import cm.backend.ecommerce.models.Users;
import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import cm.backend.ecommerce.repositories.UsersRepository;
import cm.backend.ecommerce.services.UsersServiceImp;

@ExtendWith(MockitoExtension.class)
class UsersServiceImpTest {

    @Mock
    UsersRepository usersRepository;

    @Mock
    MapperUsers mapperUsers;

    @InjectMocks
    UsersServiceImp usersService;

    // Constantes et objets de base pour les tests
    private static final Long USER_ID = 1L;
    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password123";
    private static final String UPDATED_USERNAME = "newtestuser";

    // NOTE: La constante est définie ici pour refléter l'attendu du code.
    // Si UserUtils.USER_NOT_FOUND est "User not found with id: ", on garde ceci.
    // Si c'est en français ("utilisateur non trouvé avec id: "), ajustez-la ici.
    private static final String USER_NOT_FOUND_MESSAGE_PREFIX = "User not found with id: ";

    Users userEntity;
    UsersRequestDto requestDto;
    UsersResponseDto responseDto;

    @BeforeEach
    void setup() {
        // Entité Utilisateur
        userEntity = new Users();
        userEntity.setUserId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setEmail(EMAIL);
        userEntity.setPassword(PASSWORD);
        userEntity.setAge(25);
        // Utilisation de Role.ROLE_SIMPLE_USER (selon votre modification)
        userEntity.setRole(Role.ROLE_SIMPLE_USER);
        userEntity.setStatut(Statut.INACTIVE);

        // DTO de Requête
        requestDto = new UsersRequestDto(USERNAME, EMAIL, PASSWORD);

        // DTO de Réponse
        responseDto = new UsersResponseDto(
                String.valueOf(USER_ID), USERNAME, EMAIL, Role.ROLE_ADMIN, Statut.ACTIVE);
    }

    // ------------------------- createUser Tests --------------------------------
    @Test
    void testCreateUser_shouldSaveUserWithDefaultRoleAndStatut() {
        // 1. Simuler le mapping DTO -> Entity
        when(mapperUsers.toEntity(requestDto)).thenReturn(userEntity);

        // Préparer l'entité telle qu'elle sera après la modification dans le service
        Users expectedSavedUser = new Users();
        expectedSavedUser.setUserId(USER_ID);
        expectedSavedUser.setUsername(USERNAME);
        expectedSavedUser.setEmail(EMAIL);
        expectedSavedUser.setPassword(PASSWORD);
        expectedSavedUser.setAge(25);
        expectedSavedUser.setRole(Role.ROLE_ADMIN); // Forcé par le service
        expectedSavedUser.setStatut(Statut.ACTIVE); // Forcé par le service

        // 2. Simuler l'enregistrement dans le repository
        when(usersRepository.save(any(Users.class))).thenReturn(expectedSavedUser);

        // 3. Simuler le mapping Entity -> Response DTO
        when(mapperUsers.toResponseDto(expectedSavedUser)).thenReturn(responseDto);

        // Exécution
        UsersResponseDto result = usersService.createUser(requestDto);

        // Vérifications
        assertNotNull(result);
        assertEquals(Role.ROLE_ADMIN, result.role());
        assertEquals(Statut.ACTIVE, result.statut());
        assertEquals(USERNAME, result.username());

        verify(usersRepository, times(1)).save(expectedSavedUser);
    }

    // ------------------------- getUserById Tests -------------------------------
    @Test
    void testGetUserById_shouldReturnUserResponseDto_whenFound() {
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        when(mapperUsers.toResponseDto(userEntity)).thenReturn(responseDto);

        UsersResponseDto result = usersService.getUserById(USER_ID);

        assertNotNull(result);
        assertEquals(USERNAME, result.username());
        verify(usersRepository, times(1)).findById(USER_ID);
    }

    /*
     * @Test
     * void testGetUserById_shouldThrowUserNotFoundException_whenNotFound() {
     * when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
     * 
     * // Message attendu basé sur la constante et l'ID
     * String expectedMessage = USER_NOT_FOUND_MESSAGE_PREFIX + USER_ID;
     * 
     * // On vérifie que l'exception est levée
     * Exception exception = assertThrows(UserNotFoundException.class,
     * () -> usersService.getUserById(USER_ID));
     * 
     * // On vérifie que le message est STRICTEMENT ÉGAL au message attendu
     * assertEquals(expectedMessage, exception.getMessage());
     * 
     * verify(usersRepository, times(1)).findById(USER_ID);
     * verify(mapperUsers, never()).toResponseDto(any(Users.class));
     * }
     */
    // ------------------------- getAllUsers Tests -------------------------------
    @Test
    void testGetAllUsers_shouldReturnSetOfUserResponseDtos() {
        // Deuxième entité pour tester l'ensemble
        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setRole(Role.ROLE_SIMPLE_USER);
        user2.setStatut(Statut.ACTIVE);

        UsersResponseDto responseDto2 = new UsersResponseDto(
                "2", "user2", "user2@example.com", Role.ROLE_SIMPLE_USER, Statut.ACTIVE);

        List<Users> usersList = List.of(userEntity, user2);

        when(usersRepository.findAll()).thenReturn(usersList);
        when(mapperUsers.toResponseDto(userEntity)).thenReturn(responseDto);
        when(mapperUsers.toResponseDto(user2)).thenReturn(responseDto2);

        Set<UsersResponseDto> result = usersService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.username().equals(USERNAME)));
        assertTrue(result.stream().anyMatch(dto -> dto.username().equals("user2")));
        verify(usersRepository, times(1)).findAll();
        verify(mapperUsers, times(2)).toResponseDto(any(Users.class));
    }

    @Test
    void testGetAllUsers_shouldReturnEmptySet_whenNoUsersFound() {
        when(usersRepository.findAll()).thenReturn(List.of());

        Set<UsersResponseDto> result = usersService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usersRepository, times(1)).findAll();
        verify(mapperUsers, never()).toResponseDto(any(Users.class));
    }

    // ------------------------- updateUser Tests --------------------------------
    @Test
    void testUpdateUser_shouldUpdateAndReturnUserResponseDto_whenFound() {
        UsersRequestDto updateDto = new UsersRequestDto(UPDATED_USERNAME, "new@email.com", "any");

        // Entité après la modification simulée (Simule la mise à jour des champs dans
        // le service)
        Users updatedUserEntity = new Users();
        updatedUserEntity.setUserId(USER_ID);
        updatedUserEntity.setUsername(UPDATED_USERNAME);
        updatedUserEntity.setEmail("new@email.com");
        updatedUserEntity.setRole(Role.ROLE_SIMPLE_USER); // Rôle inchangé
        updatedUserEntity.setStatut(Statut.INACTIVE); // Statut inchangé

        // DTO de Réponse après la mise à jour
        UsersResponseDto updatedResponseDto = new UsersResponseDto(
                String.valueOf(USER_ID), UPDATED_USERNAME, "new@email.com", Role.ROLE_SIMPLE_USER, Statut.INACTIVE);

        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        // Note: Le service modifie 'userEntity' puis le sauve.
        when(usersRepository.save(any(Users.class))).thenReturn(updatedUserEntity);
        when(mapperUsers.toResponseDto(updatedUserEntity)).thenReturn(updatedResponseDto);

        UsersResponseDto result = usersService.updateUser(USER_ID, updateDto);

        assertNotNull(result);
        assertEquals(UPDATED_USERNAME, result.username());
        assertEquals("new@email.com", result.email());

        // Vérifier que l'entité originale a bien été mise à jour par le service
        assertEquals(UPDATED_USERNAME, userEntity.getUsername());
        assertEquals("new@email.com", userEntity.getEmail());

        verify(usersRepository, times(1)).findById(USER_ID);
        verify(usersRepository, times(1)).save(userEntity);
        verify(mapperUsers, times(1)).toResponseDto(updatedUserEntity);
    }

    /**
     * @Test
     *       void testUpdateUser_shouldThrowUserNotFoundException_whenNotFound() {
     *       UsersRequestDto updateDto = new UsersRequestDto(UPDATED_USERNAME,
     *       "new@email.com", "any");
     *       when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
     *       String expectedMessage = USER_NOT_FOUND_MESSAGE_PREFIX + USER_ID;
     *       Exception exception = assertThrows(UserNotFoundException.class,
     *       () -> usersService.updateUser(USER_ID, updateDto));
     *       assertEquals(expectedMessage, exception.getMessage());
     *       verify(usersRepository, times(1)).findById(USER_ID);
     *       verify(usersRepository, never()).save(any(Users.class));
     *       }
     */
    // ------------------------- deleteUser Tests --------------------------------
    @Test
    void testDeleteUser_shouldDeleteExistingUser_whenFound() {
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));

        usersService.deleteUser(USER_ID);

        verify(usersRepository, times(1)).findById(USER_ID);
        verify(usersRepository, times(1)).delete(userEntity);
    }

    /**
     * @Test
     *       void testDeleteUser_shouldThrowUserNotFoundException_whenNotFound() {
     *       when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
     * 
     *       String expectedMessage = USER_NOT_FOUND_MESSAGE_PREFIX + USER_ID;
     * 
     *       UserNotFoundException exception =
     *       assertThrows(UserNotFoundException.class,
     *       () -> usersService.deleteUser(USER_ID));
     * 
     *       assertEquals(expectedMessage, exception.getMessage());
     *       verify(usersRepository, times(1)).findById(USER_ID);
     *       verify(usersRepository, never()).delete(any(Users.class));
     *       }
     */
    // ------------------------- existsByEmail Tests -----------------------------
    @Test
    void testExistsByEmail_shouldReturnTrue_whenEmailExists() {
        when(usersRepository.existsByEmail(EMAIL)).thenReturn(true);

        boolean result = usersService.existsByEmail(EMAIL);

        assertTrue(result);
        verify(usersRepository, times(1)).existsByEmail(EMAIL);
    }

    @Test
    void testExistsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        when(usersRepository.existsByEmail(EMAIL)).thenReturn(false);

        boolean result = usersService.existsByEmail(EMAIL);

        assertFalse(result);
        verify(usersRepository, times(1)).existsByEmail(EMAIL);
    }
}