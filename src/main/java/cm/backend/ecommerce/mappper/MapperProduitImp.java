package cm.backend.ecommerce.mappper;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.mappper.interfaces.IMapperProduit;
import cm.backend.ecommerce.models.produit.enumarations.Produit;
import lombok.val;

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
   String category,
   Date publicationDate,
   Year publicationYear

   for produit request 
 */

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

public class MapperProduitImp implements IMapperProduit {

    @Override
    public Produit toEntity(ProduitRequest dto) {
        Long id = new Produit().getId();
        String description = new Produit().getDescription();
        Double price = new Produit().getPrice();

        return Produit.builder()
                .id(id)
                .name(dto.name())
                .type(dto.type())
                .category(dto.category())
                .description(description)
                .price(price)
                .publicationDate(dto.publicationDate())
                .yearPublication(dto.publicationYear())
                .build();
    }

    @Override
    public ProduitRequest dto(Produit produit) {
        return ProduitRequest.builder()
                .name(produit.getName())
                .type(produit.getType())
                .category(produit.getCategory())
                .publicationDate(produit.getPublicationDate())
                .publicationYear(produit.getYearPublication())
                .build();
    }

    @Override
    public Produit toEntity(ProduitResponse produitResponse) {
        val id = new Produit().getId();

        return Produit.builder()
                .id(id)
                .name(produitResponse.name())
                .type(produitResponse.type())
                .category(produitResponse.category())
                .description(produitResponse.description())
                .price(produitResponse.price())
                .publicationDate(produitResponse.publicationDate())
                .yearPublication(produitResponse.publicationYear())
                .build();
    }

    @Override
    public ProduitResponse mapperProduitResponse(Produit produit) {
        return ProduitResponse.builder()
                .name(produit.getName())
                .type(produit.getType())
                .category(produit.getCategory())
                .description(produit.getDescription())
                .price(produit.getPrice())
                .publicationDate(produit.getPublicationDate())
                .publicationYear(produit.getYearPublication())
                .build();
    }

}
