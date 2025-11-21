package cm.backend.ecommerce.dtos.produitsdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitRequest {
    @NotBlank(message = "the name is not Blanck")
    @Size(min = 3, max = 100, message = " the espace provide for the name between 3 and 100")
    private String name;
    @NotBlank(message = "the type product is not Blanck")
    private String type;
    @NotBlank(message = "the category product is not null")
    private String category;
    @Size(max = 600)
    private String description;
    @NotNull(message = "the price is not null")
    private Double price;
    @NotNull(message = "the price is not null")
    @Min(value = 0)
    private Integer quantity;

}