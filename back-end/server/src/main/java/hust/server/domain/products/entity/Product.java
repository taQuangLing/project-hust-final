package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.ProductGuestResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal cost;

    @Column
    private String summary;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private Integer active;

    @Column
    private String img;

    @Column(name = "category_id")
    private Long categoryId;

    public ProductGuestResponse toProductGuestResponse(){
        return ProductGuestResponse.builder()
                .id(this.id)
                .img(this.img)
                .name(this.name)
                .price(this.price.longValue())
                .summary(this.summary)
                .build();
    }

}
