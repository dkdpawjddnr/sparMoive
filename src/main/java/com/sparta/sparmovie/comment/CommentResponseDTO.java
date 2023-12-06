package com.sparta.sparmovie.comment;

import java.time.LocalDateTime;

import com.sparta.sparmovie.CommonResponseDTO;
import com.sparta.sparmovie.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDTO extends CommonResponseDTO {
	private Long id;
	private String text;
	private UserDTO user;
	private LocalDateTime createDate;

	public CommentResponseDTO(Comment comment) {
		this.id = comment.getId();
		this.text = comment.getText();
		this.user = new UserDTO(comment.getUser());
		this.createDate = comment.getCreateDate();
	}
}
