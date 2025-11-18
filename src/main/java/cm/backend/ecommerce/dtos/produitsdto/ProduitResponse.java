package cm.backend.ecommerce.dtos.produitsdto;

import java.time.Year;
import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ProduitResponse(

                @Nonnull @NotBlank String name,

                @Nonnull @NotBlank String type,

                @NotBlank String category,

                String description,
                Double price,
                Date publicationDate,
                Year publicationYear) {
}
