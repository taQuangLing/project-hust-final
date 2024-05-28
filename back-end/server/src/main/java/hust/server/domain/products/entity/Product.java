package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.ProductDetailsGuestResponse;
import hust.server.domain.products.dto.response.ProductGuestResponse;
import hust.server.domain.products.dto.response.SizeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long cost;

    @Column
    private Long price;

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

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductSize> productSizes;

    @Column(name = "has_size")
    private Integer hasSize;

    public ProductGuestResponse toProductGuestResponse(){
        if (hasSize == 0){
            return ProductGuestResponse.builder()
                    .id(this.id)
                    .img(this.img)
                    .name(this.name)
                    .price(this.price)
                    .summary(this.summary)
                    .build();
        }
        ProductSize productSizeDefault = productSizes.stream().filter(productSize -> productSize.getIsDefault() == 1).findAny().get();
        return ProductGuestResponse.builder()
                .id(this.id)
                .img(this.img)
                .name(this.name + " - size " + productSizeDefault.getSize())
                .price(productSizeDefault.getPrice())
                .summary(this.summary)
                .build();

    }

    public ProductDetailsGuestResponse toProductDetailsGuestResponse(){
        if (hasSize == 0){
            return ProductDetailsGuestResponse.builder()
                    .id(this.id)
                    .img(this.img)
                    .name(this.name)
                    .price(this.price)
                    .summary(this.summary)
                    .hasSize(this.hasSize)
                    .build();
        }
        List<SizeResponse> sizeResponses = new ArrayList<>();
        this.productSizes.forEach(productSize -> {sizeResponses.add(productSize.toSizeResponse());});
        return ProductDetailsGuestResponse.builder()
                .id(this.id)
                .img(this.img)
                .name(this.name)
                .sizes(sizeResponses)
                .summary(this.summary)
                .build();
    }

}
