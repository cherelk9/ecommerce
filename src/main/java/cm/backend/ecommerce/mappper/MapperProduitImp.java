package cm.backend.ecommerce.mappper;

import org.springframework.stereotype.Component;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.models.produit.Produit;

/**
 *  private Long id;
 *  String name,
    private String type;
    private String category;
    private String description;
    private Double price;
    private Date publicationDate;
    private Year yearPublication;

    for  entity
 * 
 */

/**
 * String name,
 * String type,
 * String category,
 * Date publicationDate,
 * Year publicationYear
 * 
 * for produit request
 */

@Component
public class MapperProduitImp {

  public Produit toEntity(ProduitRequest dto) {

    if (dto == null) {
      return null;
    }

    Produit product = new Produit();
    product.setName(dto.getName());
    product.setType(dto.getType());
    product.setCategory(dto.getCategory());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());

    return product;
  }

  /**
   * String name,
   * 
   * @Nonnull @NotBlank String type,
   * 
   * @NotBlank String category,
   *           String description,
   *           Double price,
   *           Date publicationDate,
   *           Year publicationYear
   * 
   *           for produit response
   * 
   */

  public ProduitResponse mapperProduitResponse(Produit produit) {
    if (produit == null) {
      return null;
    }
    return new ProduitResponse(
        produit.getName(),
        produit.getType(),
        produit.getCategory(),
        produit.getDescription(),
        produit.getPrice(),
        produit.getQuantity(),
        produit.getPublicationDate(),
        produit.getYearPublication());
  }

  public void updateProductFromDto(ProduitRequest dto, Produit produit) {

    if (dto == null) {
      return;
    }

    if (dto.getName() != null && !dto.getName().isBlank()) {
      produit.setName(dto.getName());
    }

    if (dto.getType() != null && !dto.getType().isBlank()) {
      produit.setType(dto.getType());
    }

    if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
      produit.setCategory(dto.getCategory());
    }

    if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
      produit.setDescription(dto.getDescription());
    }

    if (dto.getPrice() != null) {
      produit.setPrice(dto.getPrice());
    }

    if (dto.getQuantity() != null) {
      produit.setQuantity(dto.getQuantity());
    }
  }

}
