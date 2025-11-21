package cm.backend.ecommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.backend.ecommerce.controllers.interfaces.IProduitController;
import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.exceptions.ProductNotFoundException;
import cm.backend.ecommerce.services.productservice.interfaces.IProduitService;
import cm.backend.ecommerce.utils.ProductUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProduitController implements IProduitController {

    private final IProduitService produitService;

    @Override
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProduitRequest produitRequest) {
        ProduitResponse response = produitService.createProduct(produitRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return produitService.getAllProducts() != null
                ? ResponseEntity.ok(produitService.getAllProducts())
                : ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable("name") String name) {
        var produitOpt = produitService.getProductByName(name);
        return produitOpt.isPresent()
                ? ResponseEntity.ok(produitOpt.get())
                : ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody ProduitRequest produitRequest) {

        ProduitResponse product = produitService.updateProduct(productId, produitRequest);
        return ResponseEntity.ok(product);
    }

    @Override
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteProduct(@PathVariable("name") String name) {
        if (produitService.getProductByName(name).isPresent()) {
            produitService.deleteProduct(name);
            return ResponseEntity.ok().body("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping("/{type}")
    public ResponseEntity<?> deleteByType(@PathVariable("type") String type) {
        return produitService.getProductByType(type).map(
                pro -> {
                    produitService.deleteAllProductsByType(type);
                    return ResponseEntity.ok().body("Products of type " + type + " deleted successfully");
                }).orElseThrow(() -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND_BY_TYPE + type));
    }

    @Override
    @GetMapping("/count/{category}")
    public ResponseEntity<?> countProducts(@PathVariable("category") String category) {
        int count = produitService.countAllProduitByCategory(category);
        return ResponseEntity.ok().body("Total products count: " + count);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(String name) {
        List<ProduitResponse> products = produitService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

}
