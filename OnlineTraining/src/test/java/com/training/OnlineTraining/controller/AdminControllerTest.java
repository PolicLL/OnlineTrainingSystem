package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.enums.Role;
import com.training.OnlineTraining.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	@Test
	@WithMockUser(authorities = {"ADMIN"})
	void getAdminPage() throws Exception {
		mockMvc.perform(get("/admins"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/admin_page"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN"})
	void getAllUsers() throws Exception {
		mockMvc.perform(get("/admins/users"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/users"))
				.andExpect(model().attribute("users", instanceOf(List.class)))
				.andExpect(model().attribute("availableRoles", Role.values()));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN"})
	void deleteUser() throws Exception {
		UUID userId = UUID.randomUUID();
		mockMvc.perform(delete("/admins/users/delete/{userId}", userId))
				.andExpect(view().name("/admin/admin_page"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN"})
	void getUpdateUserPage() throws Exception {
		UUID userId = UUID.randomUUID();

		UserDto user = new UserDto();

		when(adminService.getUserById(userId)).thenReturn(user);

		mockMvc.perform(get("/admins/users/update/{userId}", userId))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/update_user"))
				.andExpect(model().attribute("user", instanceOf(UserDto.class)));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN"})
	void updateUser() throws Exception {
		UserDto userDto = new UserDto(); // Add necessary attributes for the user DTO
		mockMvc.perform(post("/admins/users/update").flashAttr("user", userDto))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admins/users"));
	}

}
