package com.app.user;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.entity.User;
import com.app.user.repository.RefreshTokenRepository;
import com.app.user.repository.UserRepository;
import com.app.user.service.UserService;
import com.app.user.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void loginUserSuccess() {

		UserRepository userRepositoryMock = mock(UserRepository.class);
		JwtUtil jwtUtilMock = mock(JwtUtil.class);
		ModelMapper modelMapperMock = mock(ModelMapper.class);
		RefreshTokenRepository refreshTokenRepositoryMock = mock(RefreshTokenRepository.class);

		UserService userService = new UserService(userRepositoryMock, modelMapperMock, jwtUtilMock,refreshTokenRepositoryMock);

		// 2. Test data
		LoginRequest loginRequest = new LoginRequest("subham10022000@gmail.com", "subham7");
		User mappedUser = new User();
		mappedUser.setEmail("subham10022000@gmail.com");
		mappedUser.setPassword("subham7");

		// 3. Mock ModelMapper
		when(modelMapperMock.map(loginRequest, User.class)).thenReturn(mappedUser);

		// 4. Mock repository
		when(userRepositoryMock.findById("subham10022000@gmail.com"))
				.thenReturn(Optional.of(mappedUser));

		// 5. Mock JWT generation
		when(jwtUtilMock.generateToken("subham10022000@gmail.com", "user"))
				.thenReturn("mocked-access-token")
				.thenReturn("mocked-refresh-token"); // second call for refresh token

		// 6. Call the method
		LoginResponse response = userService.loginUser(loginRequest);

		// 7. Assertions
		assertNotNull(response);
		assertEquals("mocked-access-token", response.getAccessToken());
		assertEquals("mocked-refresh-token", response.getRefreshToken());
		assertEquals(3600000, response.getExpiresIn());

		// 8. Verify interactions
		verify(modelMapperMock).map(loginRequest, User.class);
		verify(userRepositoryMock).findById("subham10022000@gmail.com");
		verify(jwtUtilMock, times(2)).generateToken("subham10022000@gmail.com", "user");

	}

	@Test
	void invalidLoginTest() {

		 UserRepository userRepositoryMock = mock(UserRepository.class);
		 ModelMapper modelMapperMock = mock(ModelMapper.class);
		 JwtUtil jwtUtilMock = mock(JwtUtil.class);
		 RefreshTokenRepository refreshTokenRepositoryMock = mock(RefreshTokenRepository.class);

		 UserService userServiceMock = new UserService(userRepositoryMock,modelMapperMock,jwtUtilMock,refreshTokenRepositoryMock);

		 // test data
		 LoginRequest loginRequest = new LoginRequest("subham10022000@gmail.com" , "invalidPassword");


		 User mappedUser = new User();
		 mappedUser.setEmail("subham10022000@gmail.com");
		 mappedUser.setPassword("invalidPassword");

		 User dbUser = new User();
		 dbUser.setEmail("subham10022000@gmail.con");
		 mappedUser.setPassword("subham7");

		 //

		 when(modelMapperMock.map(loginRequest , User.class)).thenReturn(mappedUser);
		 when(userRepositoryMock.findById(mappedUser.getEmail())).thenReturn(Optional.ofNullable(null));

		 LoginResponse response = userServiceMock.loginUser(loginRequest);

        assertNull(response);

	}

}
