package com.sparta.sparmovie.test;

import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.user.User;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

import java.time.LocalDateTime;

public class PostTestUtils {

	public static Post get(Post post, User user) {
		return get(post, 1L, java.time.LocalDateTime.now(), user);
	}

	/**
	 * 테스트용 게시글 객체를 만들어주는 메서드
	 * @param post 복제할 게시글 객체
	 * @param id 설정할 아이디
	 * @param createDate 설정할 생성일시
	 * @param user 연관관계 유저
	 * @return 테스트용으로 생성된 게시글 객체
	 */
	public static Post get(Post post, Long id, LocalDateTime createDate, User user) {
		var newPost = SerializationUtils.clone(post);
		ReflectionTestUtils.setField(newPost, Post.class, "id", id, Long.class);
		ReflectionTestUtils.setField(newPost, Post.class, "createDate", createDate, LocalDateTime.class);
		newPost.setUser(user);
		return newPost;
	}
}
