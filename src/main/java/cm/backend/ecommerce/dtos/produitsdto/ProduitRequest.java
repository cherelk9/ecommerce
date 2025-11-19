package cm.backend.ecommerce.dtos.produitsdto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitRequest {
    private String name;
    private String type;
    private String category;
    private String description;
    private Double price;
    private Instant publicationDate;

}