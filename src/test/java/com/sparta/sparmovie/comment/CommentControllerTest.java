package com.sparta.sparmovie.comment;



import com.jayway.jsonpath.JsonPath;
import com.sparta.sparmovie.test.CommentTest;
import com.sparta.sparmovie.test.ControllerTest;
import com.sparta.sparmovie.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.concurrent.RejectedExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends ControllerTest implements CommentTest {

    @MockBean
    private CommentService commentService;

    @DisplayName("댓글 생성 요청")
    @Test
    void postComment() throws Exception {
        // given

        // when
        var action = mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_COMMENT_REQUEST_DTO)));

        // then
        action.andExpect(status().isCreated());
        verify(commentService, times(1)).createComment(any(CommentRequestDTO.class), eq(TEST_USER));
    }

    @Nested
    @DisplayName("댓글 삭제 요청")
    class deleteComment {
        @DisplayName("댓글 삭제 요청 성공")
        @Test
        void deleteComment_success() throws Exception {
            // given
            given(commentService.getCommentDto(eq(TEST_COMMENT_ID), eq(TEST_USER))).willReturn(TEST_COMMENT_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(delete("/api/comments/{commentId}", TEST_COMMENT_ID)
                    .accept(MediaType.APPLICATION_JSON));

            // then
            action
                    .andExpect(status().isOk());
        }
    }
}