package cm.backend.ecommerce.services.productservice.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.models.produit.enumarations.Produit;

public interface IProduitService {

    List<ProduitResponse> getAllProducts();

    Optional<ProduitResponse> getProductByName(String name);

    ProduitResponse createProduct(ProduitRequest produitRequest);

    ProduitResponse updateProduct(String name, ProduitRequest produitRequest);

    void deleteProduct(String name);

    void deleteAllProductsByType(String type);

    Function<List<Produit>, Integer> countAllProduitByCategory(String category);

}
