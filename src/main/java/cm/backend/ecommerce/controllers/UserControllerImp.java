package cm.backend.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.controllers.interfaces.IUserController;
import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.exceptions.UserNotFoundException;
import cm.backend.ecommerce.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "users", description = "API pour la gestion des utilisateur (CRUD, recherche, suppression, statistiques)")
public class UserControllerImp implements IUserController {

    private final cm.backend.ecommerce.services.interfaces.IUsersService usersService;

    @Operation(summary = "Créer un nouvel utilisateur", description = "Enregistre un nouvel utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides envoyées dans la requête", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    public ResponseEntity<?> createUser(UsersRequestDto dto) {

        if (!UserUtils.isValidEmail(dto.email())) {
            return new ResponseEntity<>(UserUtils.INVALIDE_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
        } else if (usersService.existsByEmail(dto.email())) {
            return new ResponseEntity<>(UserUtils.USER_EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(usersService.createUser(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtenir un utilisateur par ID", description = "Récupère les informations d’un utilisateur en utilisant son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable avec l’ID fourni", content = @Content),
            @ApiResponse(responseCode = "400", description = "ID fourni invalide", content = @Content)
    })
    @Override
    public ResponseEntity<?> getUserById(Long id) {
        try {
            return new ResponseEntity<>(usersService.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Mettre à jour un utilisateur", description = "Modifie les informations d’un utilisateur existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide ou données incorrectes", content = @Content),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable avec cet ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    public ResponseEntity<?> updateUser(Long id, UsersRequestDto dto) {

        var userExist = usersService.getUserById(id);
        if (userExist == null) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersService.updateUser(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur en utilisant son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable avec cet ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "ID fourni invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        var userExist = usersService.getUserById(id);
        if (userExist == null) {
            return new ResponseEntity<>(UserUtils.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Lister tous les utilisateurs", description = "Retourne la liste complète des utilisateurs enregistrés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs retournée", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsersResponseDto.class)))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers() != null
                ? new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
