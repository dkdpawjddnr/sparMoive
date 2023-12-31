package com.sparta.sparmovie.post;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PostRequestDTO {
	private String title;
	private String content;

	public PostRequestDTO(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
