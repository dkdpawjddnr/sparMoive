package com.sparta.sparmovie.test;

import com.sparta.sparmovie.comment.Comment;
import com.sparta.sparmovie.comment.CommentRequestDTO;
import com.sparta.sparmovie.comment.CommentResponseDTO;

public interface CommentTest extends CommonTest {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_TEXT = "text";

    CommentRequestDTO TEST_COMMENT_REQUEST_DTO = CommentRequestDTO.builder()
            .text(TEST_COMMENT_TEXT)
            .build();
    CommentResponseDTO TEST_COMMENT_RESPONSE_DTO = CommentResponseDTO.builder()
            .text(TEST_COMMENT_TEXT)
            .build();
    }