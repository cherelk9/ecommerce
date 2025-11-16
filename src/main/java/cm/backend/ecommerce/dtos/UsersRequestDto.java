package cm.backend.ecommerce.dtos;

import lombok.Builder;

@Builder
public record UsersRequestDto(String username, String email) {
}
