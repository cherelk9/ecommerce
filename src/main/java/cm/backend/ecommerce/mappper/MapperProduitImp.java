package cm.backend.ecommerce.mappper;

import java.time.Year;

import org.springframework.stereotype.Component;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.mappper.interfaces.IMapperProduit;
import cm.backend.ecommerce.models.produit.enumarations.Produit;

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
public class MapperProduitImp implements IMapperProduit {

  @Override
  public Produit toEntity(ProduitRequest dto) {

    Long id = new Produit().getId();
    var publicationYear = dto.getPublicationDate() != null
        ? Year.from(dto.getPublicationDate())
        : null;

    return new Produit(
        id,
        dto.getName(),
        dto.getType(),
        dto.getCategory(),
        dto.getDescription(),
        dto.getPrice(),
        dto.getPublicationDate(),
        publicationYear);
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

  @Override
  public ProduitResponse mapperProduitResponse(Produit produit) {
    return new ProduitResponse(
        produit.getName(),
        produit.getType(),
        produit.getCategory(),
        produit.getDescription(),
        produit.getPrice(),
        produit.getPublicationDate(),
        produit.getYearPublication());
  }

}
