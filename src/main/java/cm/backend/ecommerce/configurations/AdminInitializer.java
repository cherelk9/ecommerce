package cm.backend.ecommerce.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import cm.backend.ecommerce.models.Users;
import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import cm.backend.ecommerce.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            final String adminUsername = "cherel";
            final String adminEmail = "cherel@ecommerce.cm";
            final String adminPassword = "SuperPassword123!";

            if (usersRepository.findByUsername(adminUsername).isEmpty()) {

                log.info("Création de l'utilisateur ADMIN...");

                Users admin = new Users();

                admin.setUsername(adminUsername);
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Role.ROLE_ADMIN);
                admin.setStatut(Statut.ACTIVE);
                admin.setAge(26);

                usersRepository.save(admin);
                log.info("Utilisateur ADMIN créé avec succès. Username: {}", adminUsername);

            } else {
                log.info("L'utilisateur ADMIN existe déjà.");
            }
        };
    }
}