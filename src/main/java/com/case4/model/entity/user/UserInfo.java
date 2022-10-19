package com.case4.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String avatar;

    private String phoneNumber;

    private String birthDay;

    private String registerDate;

    @OneToOne
    private UserStatus userStatus;
    @OneToOne
    private User user;



    public UserInfo(String name, String email, String avatar, String phoneNumber, String birthDay, String registerDate, UserStatus userStatus, User user) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.registerDate = registerDate;
        this.userStatus = userStatus;
        this.user = user;
    }
}
