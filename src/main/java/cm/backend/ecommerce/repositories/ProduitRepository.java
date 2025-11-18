package cm.backend.ecommerce.repositories;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.backend.ecommerce.models.produit.enumarations.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Optional<Produit> findByName(String name);

    Optional<Produit> getByName(String name);

    Consumer<? super Produit> deleteAllByName(String name);

    Optional<Produit> existsByType(String type);

    boolean existsByCategory(String category);

}
