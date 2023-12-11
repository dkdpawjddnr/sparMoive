package com.sparta.sparmovie.comment;

import java.time.LocalDateTime;

import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Column
	private LocalDateTime createDate;

	// 기본 생성자를 롬복의 @Builder로 대체
	@Builder
	public Comment(String text, User user, Post post, LocalDateTime createDate) {
		this.text = text;
		this.user = user;
		this.post = post;
		this.createDate = createDate;
	}

	// 연관관계 편의 메서드
	public void setUser(User user) {
		this.user = user;
	}

	public void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}

	// 서비스 메서드
	public void setText(String text) {
		this.text = text;
	}
}
