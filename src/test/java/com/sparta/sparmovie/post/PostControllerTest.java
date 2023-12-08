package com.sparta.sparmovie.post;


import com.sparta.sparmovie.test.ControllerTest;
import com.sparta.sparmovie.test.PostTest;
import com.sparta.sparmovie.test.PostTestUtils;
import com.sparta.sparmovie.user.User;
import com.sparta.sparmovie.user.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTest implements PostTest {

    @MockBean
    private PostService postService;

    @DisplayName("게시글 생성 요청")
    @Test
    void postPost() throws Exception {
        // given

        // when
        var action = mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_POST_REQUEST_DTO)));

        // then
        action.andExpect(status().isCreated());
        verify(postService, times(1)).createPost(any(PostRequestDTO.class), eq(TEST_USER));
    }


    @Nested
    @DisplayName("게시글 조회 요청")
    class getPost {
        @DisplayName("게시글 조회 요청 성공")
        @Test
        void getPost_success() throws Exception {
            // given
            given(postService.getPostDto(eq(TEST_POST_ID))).willReturn(TEST_POST_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(get("/api/posts/{postId}", TEST_POST_ID)
                    .accept(MediaType.APPLICATION_JSON));

            // then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(TEST_POST_TITLE))
                    .andExpect(jsonPath("$.content").value(TEST_POST_CONTENT));
        }

        @DisplayName("게시글 조회 요청 실패 - 존재하지 않는 게시글")
        @Test
        void getPost_fail_postIdNotExist() throws Exception {
            // given
            given(postService.getPostDto(eq(TEST_POST_ID))).willThrow(new IllegalArgumentException());

            // when
            var action = mockMvc.perform(get("/api/posts/{postId}", TEST_POST_ID)
                    .accept(MediaType.APPLICATION_JSON));

            // then
            action
                    .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("게시글 목록 조회 요청")
    @Test
    void getPostList() throws Exception {
        // given
        var testPost1 = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now(), TEST_USER);
        var testPost2 = PostTestUtils.get(TEST_POST, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
        var testAnotherPost = PostTestUtils.get(TEST_POST, 3L, LocalDateTime.now(), TEST_ANOTHER_USER);

        given(postService.getUserPostMap()).willReturn(
                Map.of(new UserDTO(TEST_USER), List.of(new PostResponseDTO(testPost1), new PostResponseDTO(testPost2)),
                        new UserDTO(TEST_ANOTHER_USER), List.of(new PostResponseDTO(testAnotherPost))));

        // when
        var action = mockMvc.perform(get("/api/posts")
                .accept(MediaType.APPLICATION_JSON));

        // then
        action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.user.username=='" + TEST_USER.getUsername() + "')].postList[*].id")
                        .value(Matchers.containsInAnyOrder(testPost1.getId().intValue(), testPost2.getId().intValue())))
                .andExpect(jsonPath("$[?(@.user.username=='" + TEST_ANOTHER_USER.getUsername() + "')].postList[*].id")
                        .value(Matchers.containsInAnyOrder(testAnotherPost.getId().intValue())));
        verify(postService, times(1)).getUserPostMap();
    }

    @Nested
    @DisplayName("게시글 수정 요청")
    class putPost {
        @DisplayName("게시글 수정 요청 성공")
        @Test
        void putPost_success() throws Exception {
            // given
            given(postService.updatePost(eq(TEST_POST_ID), eq(TEST_POST_REQUEST_DTO), any(User.class))).willReturn(TEST_POST_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(put("/api/posts/{postId}", TEST_POST_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TEST_POST_REQUEST_DTO)));

            // then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(TEST_POST_TITLE))
                    .andExpect(jsonPath("$.content").value(TEST_POST_CONTENT));
        }

        @DisplayName("게시글 수정 요청 실패 - 권한 없음")
        @Test
        void putPost_fail_rejected() throws Exception {
            // given
            given(postService.updatePost(eq(TEST_POST_ID), eq(TEST_POST_REQUEST_DTO), any(User.class))).willThrow(new RejectedExecutionException());

            // when
            var action = mockMvc.perform(put("/api/posts/{postId}", TEST_POST_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TEST_POST_REQUEST_DTO)));

            // then
            action
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("게시글 수정 요청 실패 - 존재하지 않는 게시글")
        @Test
        void putPost_fail_illegalArgument() throws Exception {
            // given
            given(postService.updatePost(eq(TEST_POST_ID), eq(TEST_POST_REQUEST_DTO), any(User.class))).willThrow(new IllegalArgumentException());

            // when
            var action = mockMvc.perform(put("/api/posts/{postId}", TEST_POST_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TEST_POST_REQUEST_DTO)));

            // then
            action
                    .andExpect(status().isBadRequest());
        }
    }
}