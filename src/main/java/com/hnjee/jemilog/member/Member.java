package com.hnjee.jemilog.member;

import com.hnjee.jemilog.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String nickname;
    private String birthDate;
    private String imageUrl;

    public Member(String loginId, String password, String name, String phoneNumber,
                  String email, String nickname, String birthDate, String imageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
    }
}
