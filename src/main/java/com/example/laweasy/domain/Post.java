package com.example.laweasy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.laweasy.domain.core.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "activated", nullable = false)
	private boolean activated;

	@Column(name = "resolve_status", nullable = false)
	private boolean resolveStatus;

	@Column(name = "category", nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(name = "gpt_comment", nullable = false)
	private String gptComment;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	public void updateActivated(boolean activated) {
		this.activated = activated;
	}
}
