package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.service.ExerciseService;
import com.training.OnlineTraining.service.WorkoutSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class WorkoutSessionControllerTest {

	@MockBean
	private WorkoutSessionService workoutSessionService;

	@MockBean
	private ExerciseService exerciseService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void showCreateWorkoutSessionForm() throws Exception {
		UUID workoutId = UUID.randomUUID();

		mockMvc.perform(get("/workoutSession/create")
						.param("workoutID", workoutId.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("workout-session/createWorkoutSession"))
				.andExpect(model().attributeExists("workoutSessionInputDTO", "listExercises"));

		verify(exerciseService, times(1)).getAllExercises();
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void createWorkout() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutSessionInputDTO inputDTO = new WorkoutSessionInputDTO(workoutId);

		mockMvc.perform(post("/workoutSession/create")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.flashAttr("workoutDTO", inputDTO))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout/details/" + workoutId));

		verify(workoutSessionService, times(1)).createWorkoutSession(any());
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void updateWorkoutAndSessions() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutInputDTO inputDTO = new WorkoutInputDTO(workoutId);



		mockMvc.perform(post("/workoutSession/update/{workoutID}", workoutId)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.flashAttr("workout", inputDTO))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout/details/" + workoutId));

		verify(workoutSessionService, times(1)).updateWorkoutSessions(any());
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void deleteWorkoutSession() throws Exception {
		UUID workoutId = UUID.randomUUID();
		UUID workoutSessionId = UUID.randomUUID();

		mockMvc.perform(post("/workoutSession/delete/{workoutID}/{workoutSessionID}", workoutId, workoutSessionId))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout/details/" + workoutId));

		verify(workoutSessionService, times(1)).deleteWorkoutSession(workoutSessionId);
	}
}
