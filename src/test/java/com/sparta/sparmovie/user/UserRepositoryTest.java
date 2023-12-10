package com.sparta.sparmovie.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.sparmovie.Test.CommonTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest implements CommonTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("해당 이름을 가진 회원 조회")
  void findByUserName() {
    //given

    userRepository.save(TEST_USER);

    // when
    Optional<User> optionalFindUser = userRepository.findByUsername(TEST_USER.getUsername());

    // then
    assertTrue(optionalFindUser.isPresent());

    User findUser = optionalFindUser.get();
    assertEquals(TEST_USER.getId(), findUser.getId());
    assertEquals(TEST_USER.getUsername(), findUser.getUsername());

  }
}