package cm.backend.ecommerce.services.productservice;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

        var produit = mapperProduit.toEntity(produitRequest);
        produit.setPrice(new Produit().getPrice());
        produit.setDescription(new Produit().getDescription());

        return mapperProduit.mapperProduitResponse(produit);
    }

    @Override
    public ProduitResponse updateProduct(String name, ProduitRequest produitRequest) {
        return produitRepository.findByName(name).map(
                prod -> {

                    prod.setName(produitRequest.name());
                    prod.setDescription(new Produit().getDescription());
                    prod.setPrice(new Produit().getPrice());
                    prod.setPublicationDate(produitRequest.publicationDate());
                    prod.setYearPublication(produitRequest.publicationYear());

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
    public Function<List<Produit>, Integer> countAllProduitByCategory() {

        return p -> {
            p = produitRepository.findAll();
            return p != null ? p.size() : 0;
        };
    }

}
