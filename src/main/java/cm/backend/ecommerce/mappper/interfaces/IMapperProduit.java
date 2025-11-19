package cm.backend.ecommerce.mappper.interfaces;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.models.produit.enumarations.Produit;

public interface IMapperProduit {

    Produit toEntity(ProduitRequest dto);

    ProduitResponse mapperProduitResponse(Produit produit);

}