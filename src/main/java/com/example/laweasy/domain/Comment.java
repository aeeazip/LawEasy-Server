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
@Table(name = "comment")
@DynamicInsert
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post postId;

    @Column(nullable = false)
    private String status;

    @Builder
    public Comment(String content, Member memberId, Post postId, String status) {
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
        this.status = status;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}
