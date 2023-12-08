package com.sparta.sparmovie.test;

import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.post.PostRequestDTO;
import com.sparta.sparmovie.post.PostResponseDTO;

public interface PostTest extends CommonTest {

	Long TEST_POST_ID = 1L;
	String TEST_POST_TITLE = "title";
	String TEST_POST_CONTENT = "content";

	PostRequestDTO TEST_POST_REQUEST_DTO = PostRequestDTO.builder()
		.title(TEST_POST_TITLE)
		.content(TEST_POST_CONTENT)
		.build();
	PostResponseDTO TEST_POST_RESPONSE_DTO = PostResponseDTO.builder()
		.title(TEST_POST_TITLE)
		.content(TEST_POST_CONTENT)
		.build();
	Post TEST_POST = Post.builder()
		.title(TEST_POST_TITLE)
		.content(TEST_POST_CONTENT)
		.build();
	Post TEST_ANOTHER_POST = Post.builder()
		.title(ANOTHER_PREFIX + TEST_POST_TITLE)
		.content(ANOTHER_PREFIX + TEST_POST_CONTENT)
		.build();
}
