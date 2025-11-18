package cm.backend.ecommerce.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public interface IProduitController {

    @PostMapping(value = "/products", consumes = "application/json")
    ResponseEntity<?> createProduct(@Valid @RequestBody ProduitRequest produitRequest);

    @GetMapping("/products")
    ResponseEntity<?> getAllProducts();

    @GetMapping("/products/{name}")
    ResponseEntity<?> getProductByName(@PathVariable("name") String name);

    @PutMapping("/products/{name}")
    ResponseEntity<?> updateProduct(@PathVariable("name") String name,
            @Valid @RequestBody ProduitRequest produitRequest);

    @DeleteMapping("/products/{name}")
    ResponseEntity<?> deleteProduct(@PathVariable("name") String name);

    @DeleteMapping("/products/{type}")
    ResponseEntity<?> deleteByType(@PathVariable("type") String type);

    @GetMapping("/products/count/{category}")
    ResponseEntity<?> countProducts(@PathVariable("category") String category);

}
