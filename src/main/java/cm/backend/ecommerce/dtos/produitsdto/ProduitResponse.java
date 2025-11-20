package cm.backend.ecommerce.dtos.produitsdto;

import java.time.Instant;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitResponse {

        private String name;
        private String type;
        private String category;
        private String description;
        private Double price;
        private Instant publicationDate;
        private Year publicationYear;

}
