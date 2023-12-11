package com.sparta.sparmovie.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.sparmovie.Test.CommonTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentRepositoryTest implements CommonTest {

  @Autowired
  CommentRepository commentRepository;

  @Test
  @DisplayName("commentId로 comment 조회")
  void findById() {
    //given

    commentRepository.save(new Comment());
    Long commentId = 1L;

    // when
    Optional<Comment> optionalFindComment= commentRepository.findById(commentId);

    // then
    assertTrue(optionalFindComment.isPresent());

    Comment findComment = optionalFindComment.get();
    assertEquals(commentId, findComment.getId());

  }
}