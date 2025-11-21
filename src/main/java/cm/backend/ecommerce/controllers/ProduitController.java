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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "product", description = "stock de produit")
public class ProduitController implements IProduitController {

    private final IProduitService produitService;

    @Operation(summary = "create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
    @Override
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProduitRequest produitRequest) {
        ProduitResponse response = produitService.createProduct(produitRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "visualiser tout les produits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found product ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Did not find any Foos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
    @Override
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return produitService.getAllProducts() != null
                ? ResponseEntity.ok(produitService.getAllProducts())
                : ResponseEntity.noContent().build();
    }

    @Operation(summary = "visualiser tout les produits par leur nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get the Product", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
    @Override
    @GetMapping("/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable("name") String name) {
        var produitOpt = produitService.getProductByName(name);
        return produitOpt.isPresent()
                ? ResponseEntity.ok(produitOpt.get())
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Mettre à jour un produit", description = "Permet de modifier les informations d'un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide (données non valides)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produit introuvable avec l’ID fourni", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody ProduitRequest produitRequest) {

        ProduitResponse product = produitService.updateProduct(productId, produitRequest);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Supprimer un produit par nom", description = "Supprime un produit correspondant au nom fourni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit supprimé avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produit introuvable avec ce nom", content = @Content),
            @ApiResponse(responseCode = "400", description = "Nom fourni invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
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

    @Operation(summary = "Supprimer les produits par type", description = "Supprime tous les produits correspondant au type fourni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produits supprimés avec succès", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aucun produit trouvé pour ce type", content = @Content),
            @ApiResponse(responseCode = "400", description = "Type fourni invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    @DeleteMapping("/{type}")
    public ResponseEntity<?> deleteByType(@PathVariable("type") String type) {
        return produitService.getProductByType(type).map(
                pro -> {
                    produitService.deleteAllProductsByType(type);
                    return ResponseEntity.ok().body("Products of type " + type + " deleted successfully");
                }).orElseThrow(() -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND_BY_TYPE + type));
    }

    @Operation(summary = "Compter les produits par catégorie", description = "Renvoie le nombre total de produits appartenant à la catégorie fournie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre de produits retourné", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Aucun produit trouvé pour cette catégorie", content = @Content),
            @ApiResponse(responseCode = "400", description = "Catégorie fournie invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    @GetMapping("/count/{category}")
    public ResponseEntity<?> countProducts(@PathVariable("category") String category) {
        int count = produitService.countAllProduitByCategory(category);
        return ResponseEntity.ok().body("Total products count: " + count);
    }

    @Operation(summary = "Rechercher des produits", description = "Recherche et renvoie les produits correspondant au nom fourni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produits trouvés", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProduitResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Aucun produit correspondant à ce nom", content = @Content),
            @ApiResponse(responseCode = "400", description = "Paramètre name manquant ou invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @Override
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(String name) {
        List<ProduitResponse> products = produitService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

}
