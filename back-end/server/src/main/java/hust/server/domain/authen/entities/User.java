package hust.server.domain.authen.entities;

import hust.server.domain.authen.dto.response.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

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

    public CustomUserDetails toCustomUserDetails(){
        CustomUserDetails customUserDetails =  CustomUserDetails.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .active(this.active)
                .role(this.role)
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
