package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.CashierProductResponse;
import hust.server.domain.products.dto.response.GuestProductDetailsResponse;
import hust.server.domain.products.dto.response.GuestProductResponse;
import hust.server.domain.products.dto.response.SizeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static hust.server.infrastructure.utilies.Utility.formatCurrency;

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
    private List<ProductSize> sizeList;

    @Column(name = "has_size")
    private Integer hasSize;

    @Column(length = 500)
    private String description;

    public GuestProductResponse toProductGuestResponse(){
        if (hasSize == 0){
            return GuestProductResponse.builder()
                    .id(this.id)
                    .img(this.img)
                    .name(this.name)
                    .priceDisplay(formatCurrency(price))
                    .summary(this.summary)
                    .description(this.description)
                    .build();
        }
        Long minPrice = sizeList.stream().min(Comparator.comparing(ProductSize::getPrice)).orElseThrow(NoSuchElementException::new).getPrice();
        Long maxPrice = sizeList.stream().max(Comparator.comparing(ProductSize::getPrice)).orElseThrow(NoSuchElementException::new).getPrice();
        return GuestProductResponse.builder()
                .id(this.id)
                .img(this.img)
                .name(this.name)
                .priceDisplay(formatCurrency(minPrice) + " - " + formatCurrency(maxPrice))
                .summary(this.summary)
                .description(this.description)
                .build();

    }

    public GuestProductDetailsResponse toProductDetailsGuestResponse(){
        if (hasSize == 0){
            return GuestProductDetailsResponse.builder()
                    .id(this.id)
                    .img(this.img)
                    .name(this.name)
                    .priceDisplay(formatCurrency(this.price))
                    .summary(this.summary)
                    .hasSize(this.hasSize)
                    .description(this.description)
                    .price(this.price)
                    .build();
        }
        List<SizeResponse> sizeResponses = new ArrayList<>();
        this.sizeList.forEach(productSize -> {sizeResponses.add(productSize.toSizeResponse());});
        return GuestProductDetailsResponse.builder()
                .id(this.id)
                .img(this.img)
                .name(this.name)
                .sizes(sizeResponses)
                .summary(this.summary)
                .hasSize(this.hasSize)
                .description(this.description)
                .build();
    }

    public CashierProductResponse toCashierProductResponse(){
        return CashierProductResponse.builder()
                .id(id)
                .hasSize(hasSize)
                .img(img)
                .name(name)
                .price(formatCurrency(price))
                .sizes(sizeList.stream().map(ProductSize::toSizeResponse).collect(Collectors.toList()))
                .build();
    }

}
