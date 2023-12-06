package com.sparta.sparmovie.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
	private Long postId;
	private String text;
}
