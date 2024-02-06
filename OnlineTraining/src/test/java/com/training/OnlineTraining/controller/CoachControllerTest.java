package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.service.CoachService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class CoachControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CoachService coachService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void getCoachPage() throws Exception {
		mockMvc.perform(get("/coaches/coach-page"))
				.andExpect(status().isOk())
				.andExpect(view().name("coach/coach_page"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void getBecomeCoachPage() throws Exception {
		UUID userId = UUID.randomUUID();
		mockMvc.perform(get("/coaches/register").param("userId", userId.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("coach/coach_register_page"))
				.andExpect(model().attribute("userId", userId))
				.andExpect(model().attribute("coach", instanceOf(CoachDto.class)));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void becomeCoach() throws Exception {
		UUID userId = UUID.randomUUID();
		CoachDto coachDto = new CoachDto(); // Add necessary attributes for the coach DTO
		mockMvc.perform(post("/coaches/register")
						.param("userId", userId.toString())
						.flashAttr("coachDto", coachDto))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	@Test
	@WithMockUser(authorities = {"COACH"})
	void getSettings() throws Exception {
		UUID coachId = UUID.randomUUID();

		Coach coach = new Coach();
		coach.setUser(new User());

		when(coachService.getCoachById(coachId)).thenReturn(coach);

		mockMvc.perform(get("/coaches/settings").sessionAttr("coachId", coachId))
				.andExpect(status().isOk())
				.andExpect(view().name("coach/settings"))
				.andExpect(model().attribute("coach", instanceOf(Coach.class)))
				.andExpect(model().attribute("genderOptions", hasSize(2))); // Adjust the size based on your options
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void updateCoach() throws Exception {
		UUID coachId = UUID.randomUUID();
		UpdateCoachDTO updateCoachDTO = new UpdateCoachDTO(); // Add necessary attributes for the update DTO
		mockMvc.perform(put("/coaches/update").sessionAttr("coachId", coachId)
						.flashAttr("updateCoachDTO", updateCoachDTO))
				.andExpect(view().name("index"));
	}

}
