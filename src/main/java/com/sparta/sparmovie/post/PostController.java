package com.sparta.sparmovie.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import com.sparta.sparmovie.user.UserDTO;
import com.sparta.sparmovie.user.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.sparmovie.CommonResponseDTO;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<PostResponseDTO> postPost(@RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		PostResponseDTO responseDTO = postService.createPost(postRequestDTO, userDetails.getUser());

		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<CommonResponseDTO> getPost(@PathVariable Long postId) {
		try {
			PostResponseDTO responseDTO = postService.getPostDto(postId);
			return ResponseEntity.ok().body(responseDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}

	@GetMapping
	public ResponseEntity<List<PostListResponseDTO>> getPostList() {
		List<PostListResponseDTO> response = new ArrayList<>();

		Map<UserDTO, List<PostResponseDTO>> responseDTOMap = postService.getUserPostMap();

		responseDTOMap.forEach((key, value) -> response.add(new PostListResponseDTO(key, value)));

		return ResponseEntity.ok().body(response);
	}


	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> putPost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			PostResponseDTO responseDTO = postService.updatePost(postId, postRequestDTO, userDetails.getUser());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RejectedExecutionException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(new PostResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}
}
