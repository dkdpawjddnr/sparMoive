package com.sparta.sparmovie.post;

import java.util.List;

import com.sparta.sparmovie.user.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PostListResponseDTO {
	private UserDTO user;
	private List<PostResponseDTO> postList;

	public PostListResponseDTO(UserDTO user, List<PostResponseDTO> postList) {
		this.user = user;
		this.postList = postList;
	}
}
