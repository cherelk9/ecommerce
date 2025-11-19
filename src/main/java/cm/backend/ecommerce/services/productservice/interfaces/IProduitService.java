package cm.backend.ecommerce.services.productservice.interfaces;

import java.util.List;
import java.util.Optional;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;

public interface IProduitService {

    List<ProduitResponse> getAllProducts();

    Optional<ProduitResponse> getProductByName(String name);

    Optional<ProduitResponse> getProductByType(String type);

    ProduitResponse createProduct(ProduitRequest produitRequest);

    ProduitResponse updateProduct(String name, ProduitRequest produitRequest);

    void deleteProduct(String name);

    void deleteAllProductsByType(String type);

    int countAllProduitByCategory(String category);

}
