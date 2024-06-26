package hust.server.domain.products.entity;

import hust.server.domain.BaseEntity;
import hust.server.domain.products.dto.response.AdminBranchResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@Table(name = "branches")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Branch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String country;

    @Column(length = 100)
    private String address;

    @Column(name = "created_by")
    private String createdBy;

    @Column
    private Integer active;

    @Column
    private String code;

    public AdminBranchResponse toAdminBranchResponse() {
        return AdminBranchResponse.builder()
                .id(id)
                .address(address)
                .build();
    }
}
