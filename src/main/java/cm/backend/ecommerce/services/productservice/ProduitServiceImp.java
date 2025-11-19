package cm.backend.ecommerce.services.productservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.exceptions.ProductNotFoundException;
import cm.backend.ecommerce.mappper.interfaces.IMapperProduit;
import cm.backend.ecommerce.models.produit.enumarations.Produit;
import cm.backend.ecommerce.repositories.ProduitRepository;
import cm.backend.ecommerce.services.productservice.interfaces.IProduitService;
import cm.backend.ecommerce.utils.ProductUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduitServiceImp implements IProduitService {

    private final ProduitRepository produitRepository;
    private final IMapperProduit mapperProduit;

    @Override
    public List<ProduitResponse> getAllProducts() {

        return produitRepository.findAll().stream()
                .map(mapperProduit::mapperProduitResponse)
                .toList();
    }

    @Override
    public Optional<ProduitResponse> getProductByName(String name) {

        return produitRepository.findByName(name)
                .map(mapperProduit::mapperProduitResponse);
    }

    @Override
    public ProduitResponse createProduct(ProduitRequest produitRequest) {

        if (produitRequest != null) {
            throw new IllegalArgumentException(ProductUtils.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }

        return mapperProduit.mapperProduitResponse(
                produitRepository.save(
                        mapperProduit.toEntity(produitRequest)));
    }

    @Override
    @Transactional
    public ProduitResponse updateProduct(String name, ProduitRequest request) {

        Produit existingProduct = produitRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND + name));

        // Mise à jour des champs si envoys
        if (request.getType() != null)
            existingProduct.setType(request.getType());
        if (request.getCategory() != null)
            existingProduct.setCategory(request.getCategory());
        if (request.getDescription() != null)
            existingProduct.setDescription(request.getDescription());
        if (request.getPrice() != null)
            existingProduct.setPrice(request.getPrice());

        Produit saved = produitRepository.save(existingProduct);

        return mapperProduit.mapperProduitResponse(saved);
    }

    @Override
    public void deleteProduct(String name) {

        produitRepository.findByName(name)
                .ifPresent(produitRepository::delete);
    }

    @Override
    public void deleteAllProductsByType(String type) {

        Optional<Produit> optionalProduit = produitRepository.existsByType(type)
                .filter(p -> p.getType().equals(type));

        optionalProduit.ifPresent(produitRepository::delete);
    }

    @Override
    public int countAllProduitByCategory(String category) {

        return (int) produitRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category))
                .count();
    }

    @Override
    public Optional<ProduitResponse> getProductByType(String type) {
        return produitRepository.findByName(type)
                .map(mapperProduit::mapperProduitResponse);
    }

}
