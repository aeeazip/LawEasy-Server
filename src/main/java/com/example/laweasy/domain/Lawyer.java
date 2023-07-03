package com.example.laweasy.domain;

import com.example.laweasy.domain.core.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "lawyer")
@DynamicInsert
public class Lawyer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lawyer_id")
    private Long lawyerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(name = "phone_price", nullable = false)
    private int phonePrice;

    @Column(name = "video_price", nullable = false)
    private int videoPrice;

    @Column(name = "company", nullable = false)
    private String company;

    @Builder
    public Lawyer(String name, String description, String category, int phonePrice, int videoPrice, String company) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.phonePrice = phonePrice;
        this.videoPrice = videoPrice;
        this.company = company;
    }
}
