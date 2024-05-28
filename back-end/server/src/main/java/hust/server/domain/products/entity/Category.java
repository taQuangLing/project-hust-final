package hust.server.domain.products.entity;

import hust.server.domain.products.dto.response.CategoryGuestResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public CategoryGuestResponse toCategoryGuestResponse(){
        return CategoryGuestResponse.builder()
                .id(this.id)
                .name(this.name)
                .img(this.img)
                .products(new ArrayList<>())
                .build();
    }
}
