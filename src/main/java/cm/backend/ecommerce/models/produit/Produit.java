package cm.backend.ecommerce.models.produit;

import java.io.Serializable;
import java.time.Instant;
import java.time.Year;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Instant publicationDate;
    @Column
    private Year yearPublication;

    @PrePersist
    protected void onCreate() {
        publicationDate = Instant.now();
        yearPublication = Year.now();
    }

    @PreUpdate
    protected void onUpdate() {
        yearPublication = Year.now();
    }

}
