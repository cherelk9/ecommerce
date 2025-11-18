package cm.backend.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.controllers.interfaces.IProduitController;
import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.exceptions.ProductNotFoundException;
import cm.backend.ecommerce.services.productservice.interfaces.IProduitService;
import cm.backend.ecommerce.utils.ProductUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequiredArgsConstructor
public class ProduitController implements IProduitController {

    private final IProduitService produitService;

    @Override
    public ResponseEntity<?> createProduct(@Valid ProduitRequest produitRequest) {

        if (produitRequest != null) {
            return ResponseEntity.badRequest().body("ProduitRequest cannot be null");
        }
        var produitResponse = produitService.createProduct(produitRequest);
        return ResponseEntity.ok(produitResponse);
    }

    @Override
    public ResponseEntity<?> getAllProducts() {
        return produitService.getAllProducts() != null
                ? ResponseEntity.ok(produitService.getAllProducts())
                : ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getProductByName(String name) {
        var produitOpt = produitService.getProductByName(name);
        return produitOpt.isPresent()
                ? ResponseEntity.ok(produitOpt.get())
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> updateProduct(String name, @Valid ProduitRequest produitRequest) {
        return produitService.updateProduct(name, produitRequest) != null
                ? ResponseEntity.ok(produitService.updateProduct(name, produitRequest))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteProduct(String name) {
        if (produitService.getProductByName(name).isPresent()) {
            produitService.deleteProduct(name);
            return ResponseEntity.ok().body("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> deleteByType(String type) {
        return produitService.getProductByType(type).map(
                pro -> {
                    produitService.deleteAllProductsByType(type);
                    return ResponseEntity.ok().body("Products of type " + type + " deleted successfully");
                }).orElseThrow(() -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND_BY_TYPE + type));
    }

    @Override
    public ResponseEntity<?> countProducts(String category) {
        var count = produitService.countAllProduitByCategory(category);
        return ResponseEntity.ok().body("Total products count: " + count);
    }

}
