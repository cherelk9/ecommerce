package cm.backend.ecommerce.dtos;

import cm.backend.ecommerce.models.enumarations.Role;
import cm.backend.ecommerce.models.enumarations.Statut;
import lombok.Builder;

@Builder
public record UsersResponseDto(String userId, String username, String email, Role role, Statut statut) {

}
