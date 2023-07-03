package com.example.laweasy.domain;

import com.example.laweasy.domain.core.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="member")
@DynamicInsert
@DynamicUpdate
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Builder
    public Member(String email, String password, Role role, String nickname) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
    }

    public void encryptPassword(String password) {
        this.password = password;
    }
}
