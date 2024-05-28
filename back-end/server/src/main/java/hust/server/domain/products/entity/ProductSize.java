package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.SizeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "product_size")
@AllArgsConstructor
@NoArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private Long price;

    @Column(name = "is_default")
    private Integer isDefault;

    public SizeResponse toSizeResponse(){
        return SizeResponse.builder()
                .id(this.id)
                .size(this.size)
                .price(this.price)
                .isDefault(this.isDefault)
                .build();
    }
}
