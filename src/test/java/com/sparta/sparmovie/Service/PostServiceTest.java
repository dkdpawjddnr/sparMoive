package com.sparta.sparmovie.Service;

import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.post.PostRepository;
import com.sparta.sparmovie.post.PostRequestDTO;
import com.sparta.sparmovie.post.PostService;
import com.sparta.sparmovie.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void testCreatePost() {
        // 테스트 데이터
        PostRequestDTO postRequestDTO = new PostRequestDTO("제목", "댓글");
        postRequestDTO.setTitle("Test Title");
        postRequestDTO.setContent("Test Content");

        User user = new User();
        user.setId(1L);
        user.setUsername("dd");
        user.setPassword("i123");

        // Mock 데이터 설정
        when(postRepository.save(any())).thenReturn(new Post());

        // 테스트 수행
        postService.createPost(postRequestDTO, user);

        // Mock이 제대로 호출되었는지 검증
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void testGetPostDto() {
        // 테스트 데이터
        Long postId = 1L;

        // Mock 데이터 설정
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        // 테스트 수행
        postService.getPostDto(postId);

        // Mock이 제대로 호출되었는지 검증
        verify(postRepository, times(1)).findById(postId);
    }
}
