package com.sparta.sparmovie.Post;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.sparmovie.Test.PostTest;
import com.sparta.sparmovie.Test.PostTestUtils;
import com.sparta.sparmovie.post.PostRepository;
import com.sparta.sparmovie.user.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest implements PostTest {

  @Autowired
  PostRepository postRepository;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository.save(TEST_USER);
  }


  @Test
  @DisplayName("생성일시 기준 내림차순 정렬 조회")
  void findAll() {
    // given

    var testPost1 = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now().minusMinutes(2),
        TEST_USER);
    var testPost2 = PostTestUtils.get(TEST_POST, 2L, LocalDateTime.now().minusMinutes(1),
        TEST_USER);
    var testPost3 = PostTestUtils.get(TEST_POST, 3L, LocalDateTime.now(), TEST_USER);
    postRepository.save(testPost1);
    postRepository.save(testPost2);
    postRepository.save(testPost3);

    // when
    var resultPostList = postRepository.findAll(Sort.by(Direction.DESC, "createDate"));

    // then
    assertThat(resultPostList.get(0)).isEqualTo(testPost3);
    assertThat(resultPostList.get(1)).isEqualTo(testPost2);
    assertThat(resultPostList.get(2)).isEqualTo(testPost1);
  }
}
