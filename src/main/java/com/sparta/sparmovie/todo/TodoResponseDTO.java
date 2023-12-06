package com.sparta.sparmovie.todo;

import java.time.LocalDateTime;

import com.sparta.sparmovie.user.UserDTO;
import com.sparta.sparmovie.CommonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class TodoResponseDTO extends CommonResponseDTO {
	private Long id;
	private String title;
	private String content;
	private Boolean isCompleted;
	private UserDTO user;
	private LocalDateTime createDate;

	public TodoResponseDTO(String msg, Integer statusCode) {
		super(msg, statusCode);
	}

	public TodoResponseDTO(Todo todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.content = todo.getContent();
		this.isCompleted = todo.getIsCompleted();
		this.user = new UserDTO(todo.getUser());
		this.createDate = todo.getCreateDate();
	}
}
