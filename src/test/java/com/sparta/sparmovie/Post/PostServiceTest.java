package com.sparta.sparmovie.Post;

import static com.sparta.sparmovie.Test.CommonTest.TEST_ANOTHER_USER;
import static com.sparta.sparmovie.Test.CommonTest.TEST_USER;
import static com.sparta.sparmovie.Test.CommonTest.TEST_USER_ID;
import static com.sparta.sparmovie.Test.PostTest.TEST_POST;
import static com.sparta.sparmovie.Test.PostTest.TEST_POST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.sparmovie.Test.PostTestUtils;
import com.sparta.sparmovie.post.Post;
import com.sparta.sparmovie.post.PostRepository;
import com.sparta.sparmovie.post.PostRequestDTO;
import com.sparta.sparmovie.post.PostResponseDTO;
import com.sparta.sparmovie.post.PostService;
import com.sparta.sparmovie.user.User;
import com.sparta.sparmovie.user.UserDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostService postService;

  @DisplayName("Post 생성")
  @Test
  public void createPost() {
    // 테스트 데이터
    Post testPost = Post.builder()
        .title("제목")
        .content("내용")
        .build();
    User user = new User("username","password");
    testPost.setUser(user);

    PostRequestDTO postRequestDTO = PostRequestDTO
        .builder().title("제목").content("내용").build();


    // Mock 데이터 설정
    when(postRepository.save(any())).thenReturn(testPost);

    // 테스트 수행
    var result =postService.createPost(postRequestDTO, user);

    // Mock이 제대로 호출되었는지 검증
    verify(postRepository, times(1)).save(any());


    var expect = new PostResponseDTO(testPost);
    assertThat(result).isEqualTo(expect);
  }

  @Test
  public void GetPostDto() {
    // 테스트 데이터
    Long postId = 1L;

    Post testPost = Post.builder()
        .title("제목")
        .content("내용")
        .build();
    User user = new User("username","password");
    testPost.setUser(user);

    // Mock 데이터 설정
    when(postRepository.findById(postId)).thenReturn(Optional.of(testPost));

    // 테스트 수행
    var result = postService.getPostDto(postId);

    // Mock이 제대로 호출되었는지 검증
    verify(postRepository, times(1)).findById(postId);

    var expect = new PostResponseDTO(testPost);
    assertThat(result).isEqualTo(expect);

  }


  @Test
  public void getPost(){
    //Given
    Post testPost = Post.builder()
        .title("제목")
        .content("내용")
        .build();
    User user = new User("username","password");
    testPost.setUser(user);

    given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

    //When
    var result = postService.getPost(1L);
    //then
    assertThat(result).isEqualTo(testPost);
  }

  @DisplayName("Post 리스트 맵 (최신순)")
  @Test
  void getUserPostMap() {
//     given
    var testPost1 = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now(), TEST_USER);
    var testPost2 = PostTestUtils.get(TEST_POST, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
    var testAnotherTodo = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now(), TEST_ANOTHER_USER);


    given(postRepository.findAll(any(Sort.class))).willReturn(
        List.of(testPost1, testPost2, testAnotherTodo));

    // when
    var result = postService.getUserPostMap();

    // then
    assertThat(result.get(new UserDTO(TEST_USER)).get(0)).isEqualTo(new PostResponseDTO(testPost1));
    assertThat(result.get(new UserDTO(TEST_USER)).get(1)).isEqualTo(new PostResponseDTO(testPost2));
    assertThat(result.get(new UserDTO(TEST_ANOTHER_USER)).get(0)).isEqualTo(new PostResponseDTO(testAnotherTodo));
  }

  @DisplayName("Post 수정")
  @Test
  void updateTodo() {
    // given
    ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
    var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
    given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));

    // when
    var request = PostRequestDTO.builder().title("updatetitle").content("updatecontent").build();
    var result = postService.updatePost(TEST_POST_ID, request, TEST_USER);

    // then
    assertThat(result).isEqualTo(new PostResponseDTO(testPost));
  }



}