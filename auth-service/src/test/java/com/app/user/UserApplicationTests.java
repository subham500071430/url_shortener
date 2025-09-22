package com.app.user;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.entity.User;
import com.app.user.repository.AuthRepository;
import com.app.user.service.AuthService;
import com.app.user.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void loginUserSuccess() {

        AuthRepository repository = mock(AuthRepository.class);
        JwtUtil jwtUtilMock = mock(JwtUtil.class);
        ModelMapper modelMapperMock = mock(ModelMapper.class);

        AuthService authService = new AuthService(repository, modelMapperMock, jwtUtilMock);

        // 2. Test data
        LoginRequest loginRequest = new LoginRequest("subham10022000@gmail.com", "subham7");
        User mappedUser = new User();
        mappedUser.setEmail("subham10022000@gmail.com");
        mappedUser.setPassword("subham7");

        // 3. Mock ModelMapper
        when(modelMapperMock.map(loginRequest, User.class)).thenReturn(mappedUser);

        // 4. Mock repository
        when(repository.findById("subham10022000@gmail.com"))
                .thenReturn(Optional.of(mappedUser));

        // 5. Mock JWT generation
        when(jwtUtilMock.generateToken("subham10022000@gmail.com", "user", 36000))
                .thenReturn("mocked-access-token")
                .thenReturn("mocked-refresh-token"); // second call for refresh token

        // 6. Call the method
        LoginResponse response = authService.loginUser(loginRequest);

        // 7. Assertions
        assertNotNull(response);
        assertEquals("mocked-access-token", response.getAccessToken());
        assertEquals("mocked-refresh-token", response.getRefreshToken());
        assertEquals(36000, response.getExpiresIn());
    }

    @Test
    void invalidLoginTest() {

        AuthRepository authRepository = mock(AuthRepository.class);
        ModelMapper modelMapperMock = mock(ModelMapper.class);
        JwtUtil jwtUtilMock = mock(JwtUtil.class);

        AuthService authServiceMock = new AuthService(authRepository, modelMapperMock, jwtUtilMock);

        // test data
        LoginRequest loginRequest = new LoginRequest("subham10022000@gmail.com", "invalidPassword");

        User mappedUser = new User();
        mappedUser.setEmail("subham10022000@gmail.com");
        mappedUser.setPassword("invalidPassword");

        User dbUser = new User();
        dbUser.setEmail("subham10022000@gmail.con");
        mappedUser.setPassword("subham7");

        when(modelMapperMock.map(loginRequest, User.class)).thenReturn(mappedUser);
        when(authRepository.findById(mappedUser.getEmail())).thenReturn(Optional.ofNullable(null));

        LoginResponse response = authServiceMock.loginUser(loginRequest);

        assertNull(response);

    }

}
