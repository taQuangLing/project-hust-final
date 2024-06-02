package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.CashierCategoryResponse;
import hust.server.domain.products.dto.response.GuestCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
@Builder
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String summary;

    @Column
    private String img;

    @Column
    private Integer active;

    public GuestCategoryResponse toCategoryGuestResponse(){
        return GuestCategoryResponse.builder()
                .id(this.id)
                .name(this.name)
                .img(this.img)
                .products(new ArrayList<>())
                .build();
    }

    public CashierCategoryResponse toCashierCategoryResponse(){
        return CashierCategoryResponse.builder()
                .id(this.id)
                .name(this.name)
                .img(this.img)
                .products(new ArrayList<>())
                .build();
    }
}
