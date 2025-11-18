package cm.backend.ecommerce.mappper.interfaces;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.models.produit.enumarations.Produit;

public interface IMapperProduit {

    Produit toEntity(ProduitRequest dto);

    ProduitRequest dto(Produit produit);

    Produit toEntity(ProduitResponse produitResponse);

    ProduitResponse mapperProduitResponse(Produit produit);

}