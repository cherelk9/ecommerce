package cm.backend.ecommerce.services.productservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
    public ProduitResponse updateProduct(String name, ProduitRequest produitRequest) {
        return produitRepository.findByName(name).map(
                prod -> {

                    Produit product = mapperProduit.toEntity(produitRequest);

                    prod.setName(product.getName());
                    prod.setDescription(product.getDescription());
                    prod.setPrice(product.getPrice());
                    prod.setPublicationDate(product.publicationDate());
                    prod.setYearPublication(product.publicationYear());

                    var produit = produitRepository.save(prod);
                    return mapperProduit.mapperProduitResponse(produit);

                }).orElseThrow(() -> new ProductNotFoundException(ProductUtils.PRODUCT_NOT_FOUND + name));
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
