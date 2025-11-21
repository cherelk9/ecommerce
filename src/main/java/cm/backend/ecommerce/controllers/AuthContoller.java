package cm.backend.ecommerce.controllers;

// removed incorrect Couchbase Authentication import
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.dtos.UsersRequestDto;
import cm.backend.ecommerce.dtos.UsersResponseDto;
import cm.backend.ecommerce.mappper.interfaces.MapperUsers;
import cm.backend.ecommerce.models.Users;
import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import cm.backend.ecommerce.repositories.UsersRepository;
import cm.backend.ecommerce.utils.JwtUtils;
import cm.backend.ecommerce.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthContoller {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MapperUsers mapperUsers;

    @Operation(summary = "connexion d'un utilisateur", description = "la connection d'un utilisateur lorsqu'il est deja inscript")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur connecte avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides envoyées dans la requête", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            String token = jwtUtils.generateToken(authentication);
            return ResponseEntity.ok().body(new JwtResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @Operation(summary = "Inscription un nouvel utilisateur", description = "Enregistre un nouvel utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides envoyées dans la requête", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UsersRequestDto loginRequest) {
        if (usersRepository.existsByEmail(loginRequest.email()) ||
                usersRepository.existsByUsername(loginRequest.username())) {
            return ResponseEntity.badRequest().body(UserUtils.EXIST_By_NAME_OR_EMAIL);
        }

        Users user = Users.builder()
                .email(loginRequest.email())
                .username(loginRequest.username())
                .password(passwordEncoder.encode(loginRequest.password()))
                .role(Role.ROLE_SIMPLE_USER)
                .statut(Statut.ACTIVE)
                .build();

        Users saveUsers = usersRepository.save(user);
        UsersResponseDto response = mapperUsers.toResponseDto(saveUsers);
        return ResponseEntity.status(201).body(response);
    }

    record LoginRequest(String username, String email, String password) {
    }

    public record JwtResponse(String token) {
    }

}
