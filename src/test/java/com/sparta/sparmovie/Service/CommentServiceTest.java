package com.sparta.sparmovie.Service;

import com.sparta.sparmovie.comment.Comment;
import com.sparta.sparmovie.comment.CommentRepository;
import com.sparta.sparmovie.comment.CommentRequestDTO;
import com.sparta.sparmovie.comment.CommentService;
import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.post.PostService;
import com.sparta.sparmovie.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostService postService;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testCreateComment() {
        // 테스트 데이터
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setPostId(1L);
        commentRequestDTO.setText("Test Comment");

        User user = new User();
        user.setId(1L);


        // Mock 데이터 설정
        when(postService.getPost(any())).thenReturn(
                Post.builder().comments(new ArrayList<>()).build());

        when(commentRepository.save(any())).thenReturn(new Comment());

        Post.builder().comments(new ArrayList<>()).build();
        // 테스트 수행
        commentService.createComment(commentRequestDTO, user);
        // commentService.deleteComment(1L, user);
    }

}
