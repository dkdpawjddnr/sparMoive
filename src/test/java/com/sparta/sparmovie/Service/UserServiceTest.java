package com.sparta.sparmovie.Service;
import com.sparta.sparmovie.user.User;
import com.sparta.sparmovie.user.UserRepository;
import com.sparta.sparmovie.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        // 테스트 데이터
        String username = "testUser";
        String password = "testPassword";

        // Mock 데이터 설정
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // 테스트 수행
        userService.createUser(username, password);
    }
}
