package com.dahub.auth.entity;

import com.dahub.auth.enums.RoleType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Table(name = "USER")
public class UserEntity extends BaseEntity {

    @Id
    private String userId;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private String refreshToken;
    private String userName;
    private String email;

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
