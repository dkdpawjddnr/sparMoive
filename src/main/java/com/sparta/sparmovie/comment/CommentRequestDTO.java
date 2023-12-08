package com.sparta.sparmovie.comment;

import com.sparta.sparmovie.post.PostRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentRequestDTO {
	private Long postId;
	private String text;


}
