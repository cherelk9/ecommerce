package cm.backend.ecommerce.models;

import java.io.Serializable;

import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    private int age;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Role role;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Statut statut;
}
