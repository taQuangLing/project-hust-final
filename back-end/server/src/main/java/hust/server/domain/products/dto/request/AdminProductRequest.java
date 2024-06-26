package hust.server.domain.products.dto.request;

import hust.server.domain.products.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminProductRequest {
    private String userId;
    private String name;
    private String description;
    private String img;
    private Boolean isAllForMenu;
    private Long categoryId;

    public Product toProductEntity() {
        return Product.builder()
                .createdBy(userId)
                .name(name)
                .price(0L)
                .description(description)
                .img(img)
                .categoryId(categoryId)
                .active(1)
                .build();
    }
}
