package cm.backend.ecommerce.models;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
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
    private Role role;
    private Statut statut;
}
