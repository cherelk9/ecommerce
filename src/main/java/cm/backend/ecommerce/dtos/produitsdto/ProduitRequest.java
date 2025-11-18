package cm.backend.ecommerce.dtos.produitsdto;

import java.time.Year;
import java.util.Date;

import lombok.Builder;

@Builder
public record ProduitRequest(
        String name,
        String type,
        String category,
        Date publicationDate,
        Year publicationYear) {
}