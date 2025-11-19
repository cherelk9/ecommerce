package cm.backend.ecommerce.models.produit.enumarations;

import java.io.Serializable;
import java.time.Instant;
import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = " PRODUCTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String category;

    private String description;

    private Double price;

    private Instant publicationDate = Instant.now();

    private Year yearPublication = Year.now();

}
