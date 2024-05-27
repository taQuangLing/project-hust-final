package hust.server.domain.authen.entities;

import hust.server.domain.authen.dto.response.UserResponse;
import hust.server.domain.products.entity.Branch;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    @Column
    String username;

    @Column
    String password;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String role;

    @Column
    Integer active;

    @Column(name = "is_guest")
    Boolean isGuest;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    Branch branch;

    public CustomUserDetails toCustomUserDetails(){
        CustomUserDetails customUserDetails =  CustomUserDetails.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .branchId(this.branch.getId())
                .build();
        return customUserDetails;
    }

    public UserResponse toUserReponse(){
        return UserResponse.builder()
                .id(this.id)
                .username(this.username)
                .active(this.active)
                .role(this.role)
                .build();
    }
}
