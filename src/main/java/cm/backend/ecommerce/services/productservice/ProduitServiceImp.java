package cm.backend.ecommerce.services.productservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cm.backend.ecommerce.dtos.produitsdto.ProduitRequest;
import cm.backend.ecommerce.dtos.produitsdto.ProduitResponse;
import cm.backend.ecommerce.mappper.interfaces.IMapperProduit;
import cm.backend.ecommerce.models.produit.enumarations.Produit;
import cm.backend.ecommerce.repositories.ProduitRepository;
import cm.backend.ecommerce.services.productservice.interfaces.IProduitService;
import cm.backend.ecommerce.utils.ProductUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.info(ProductUtils.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }

        return mapperProduit.mapperProduitResponse(
                produitRepository.save(
                        mapperProduit.toEntity(produitRequest)));
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

    @Override
    @Transactional
    public void updateProduct(Long productId, ProduitRequest produitRequest) {

        if (produitRequest == null) {
            log.info(ProductUtils.PRODUCT_REQUEST_CANNOT_BE_NULL);
        }

        produitRepository.findById(productId).ifPresent(
                p -> {
                    Produit e = mapperProduit.toEntity(produitRequest);
                    p.setName(e.getName());
                    p.setType(e.getType());
                    p.setCategory(e.getCategory());
                    p.setDescription(e.getDescription());
                    p.setPrice(e.getPrice());
                    produitRepository.save(p);

                });
    }

}
