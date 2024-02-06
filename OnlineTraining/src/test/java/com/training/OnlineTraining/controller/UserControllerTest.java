package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.service.MailService;
import com.training.OnlineTraining.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private MailService mailService;

	private final MockHttpSession session = new MockHttpSession();


	@Test
	void getRegisterPage() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("registration/register_page"))
				.andExpect(model().attributeExists("registerRequest"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "USER"})
	void getBecomeClientOrCoachPage() throws Exception {
		UUID userId = UUID.randomUUID();
		session.setAttribute("userId", userId);

		mockMvc.perform(get("/additions").session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("auth/become_client_or_coach_page"))
				.andExpect(model().attribute("userId", userId));
	}

	@Test
	void getLoginPage() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("auth/login_page"))
				.andExpect(model().attributeExists("loginRequest"));
	}

	@Test
	void registerSuccess() throws Exception {
		UserDto request = new UserDto();
		request.setEmail("test@example.com");
		request.setPassword("password");

		when(userService.registerUser(request)).thenReturn(new User(UUID.randomUUID(), "test@example.com", "password"));

		ResultActions result = mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", request.getEmail())
				.param("password", request.getPassword())
				.param("confirmPassword", request.getPassword())
		);

		result.andExpect(status().isOk())
				.andExpect(view().name("/auth/become_client_or_coach_page"))
				.andExpect(model().attributeExists("userId"));

		verify(mailService, times(1)).sendEmailAsync(any(), any(), any());
	}

	@Test
	void registerPasswordMismatch() throws Exception {
		UserDto request = new UserDto();
		request.setPassword("password");

		ResultActions result = mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("password", request.getPassword())
				.param("confirmPassword", "wrongPassword")
		);

		result.andExpect(status().isOk())
				.andExpect(view().name("registration/register_page"))
				.andExpect(model().attributeExists("error"))
				.andExpect(model().attributeExists("registerRequest"));
	}

	@Test
	void registerException() throws Exception {
		UserDto request = new UserDto();
		request.setPassword("password");

		when(userService.registerUser(request)).thenThrow(new RuntimeException("Registration error"));

		ResultActions result = mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("password", request.getPassword())
				.param("confirmPassword", request.getPassword())
		);

		result.andExpect(status().isOk())
				.andExpect(view().name("registration/register_page"))
				.andExpect(model().attributeExists("error"))
				.andExpect(model().attributeExists("registerRequest"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH", "CLIENT", "USER"})
	void logout() throws Exception {
		mockMvc.perform(get("/logout"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(request().sessionAttributeDoesNotExist("SPRING_SECURITY_CONTEXT"));
	}

}
