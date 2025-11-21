package cm.backend.ecommerce.services.productservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.exceptions.ProductNotFoundException;
import cm.backend.ecommerce.mappper.MapperProduitImp;
import cm.backend.ecommerce.models.produit.Produit;
import cm.backend.ecommerce.repositories.ProduitRepository;
import cm.backend.ecommerce.services.productservice.interfaces.IProduitService;
import cm.backend.ecommerce.utils.ProductUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProduitServiceImp implements IProduitService {

    private final ProduitRepository produitRepository;
    private final MapperProduitImp mapperProduitImp;

    @Override
    public List<ProduitResponse> getAllProducts() {

        return produitRepository.findAll().stream()
                .map(mapperProduitImp::mapperProduitResponse)
                .toList();
    }

    @Override
    public Optional<ProduitResponse> getProductByName(String name) {

        return produitRepository.findByName(name)
                .map(mapperProduitImp::mapperProduitResponse);
    }

    @Override
    public ProduitResponse createProduct(ProduitRequest produitRequest) {

        if (produitRequest == null) {
            log.info(ProductUtils.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }

        Produit produit = mapperProduitImp.toEntity(produitRequest);
        Produit saveProduit = produitRepository.save(produit);

        return mapperProduitImp.mapperProduitResponse(saveProduit);
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
                .map(mapperProduitImp::mapperProduitResponse);
    }

    @Override
    public ProduitResponse updateProduct(Long productId, ProduitRequest produitRequest) {
        Produit product = produitRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND + productId));

        mapperProduitImp.updateProductFromDto(produitRequest, product);

        Produit updateProduct = produitRepository.save(product);
        return mapperProduitImp.mapperProduitResponse(updateProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduitResponse> searchProductsByName(String name) {
        return produitRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapperProduitImp::mapperProduitResponse)
                .toList();
    }

}
