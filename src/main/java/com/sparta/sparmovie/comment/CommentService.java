package com.sparta.sparmovie.comment;

import java.time.LocalDateTime;
import java.util.concurrent.RejectedExecutionException;

import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.post.PostService;
import com.sparta.sparmovie.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostService postService;

	public CommentResponseDTO createComment(CommentRequestDTO dto, User user) {
		Post post = postService.getPost(dto.getPostId());

		// Comment 클래스의 빌더 패턴을 사용하여 Comment 객체 생성
		Comment comment = Comment.builder()
				.text(dto.getText())  // CommentRequestDTO에서 필요한 값들을 가져와 설정
				.user(user)
				.post(post)
				.createDate(LocalDateTime.now())  // 현재 날짜 및 시간으로 설정 또는 필요에 따라 수정
				.build();

		commentRepository.save(comment);

		return new CommentResponseDTO(comment);
	}


	@Transactional
	public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, User user) {
		Comment comment = getUserComment(commentId, user);

		comment.setText(commentRequestDTO.getText());

		return new CommentResponseDTO(comment);
	}

	public void deleteComment(Long commentId, User user) {
		Comment comment = getUserComment(commentId, user);

		commentRepository.delete(comment);
	}

	private Comment getUserComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

		if(!user.getId().equals(comment.getUser().getId())) {
			throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
		}
		return comment;
	}

	public CommentResponseDTO getCommentDto(Long commentId, User user) {
		Comment comment = getUserComment(commentId, user);
		return new CommentResponseDTO(comment);
	}
}

