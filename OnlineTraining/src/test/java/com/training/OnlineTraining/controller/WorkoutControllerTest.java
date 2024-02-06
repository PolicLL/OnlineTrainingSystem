package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import com.training.OnlineTraining.service.WorkoutService;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class WorkoutControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WorkoutService workoutService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void showCreateWorkoutForm() throws Exception {
		mockMvc.perform(get("/workout/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("workout/createWorkout"))
				.andExpect(model().attributeExists("workoutInputDTO"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void createWorkout() throws Exception {
		UUID contractId = UUID.randomUUID();
		WorkoutInputDTO inputDTO = new WorkoutInputDTO();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("contractID", contractId);

		mockMvc.perform(post("/workout/create")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.session(session)
						.flashAttr("workoutDTO", inputDTO))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout"));

		verify(workoutService, times(1)).createWorkout(any(), eq(contractId));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH", "CLIENT"})
	void getAllWorkoutsForContract() throws Exception {
		UUID contractId = UUID.randomUUID();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("contractID", contractId);

		UUID clientId = UUID.randomUUID();
		session.setAttribute("clientId", clientId);

		List<WorkoutOutputDTO> workouts = Arrays.asList(new WorkoutOutputDTO(), new WorkoutOutputDTO());
		when(workoutService.getWorkoutsByContractID(contractId)).thenReturn(workouts);

		mockMvc.perform(get("/workout").session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("workout/workoutClientList"))
				.andExpect(model().attribute("workoutsList", workouts));
	}


	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH", "CLIENT"})
	void showWorkoutDetails() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutOutputDTO workout = new WorkoutOutputDTO();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("clientId", UUID.randomUUID());

		when(workoutService.getWorkoutById(workoutId)).thenReturn(workout);

		mockMvc.perform(get("/workout/details/{id}", workoutId)
						.session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("workout/workoutClientDetails"))
				.andExpect(model().attribute("workout", workout))
				.andExpect(model().attributeExists("listExercises"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH", "CLIENT"})
	void showUpdateWorkoutForm() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutOutputDTO workout = new WorkoutOutputDTO();
		List<WorkoutStatus> workoutStatuses = Arrays.asList(WorkoutStatus.values());
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("clientId", UUID.randomUUID());

		when(workoutService.getWorkoutById(workoutId)).thenReturn(workout);

		mockMvc.perform(get("/workout/update/{id}", workoutId)
						.session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("workout/updateClientWorkout"))
				.andExpect(model().attribute("workout", workout))
				.andExpect(model().attribute("workoutStatuses", workoutStatuses));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void updateWorkout() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutInputDTO inputDTO = new WorkoutInputDTO();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("contractID", UUID.randomUUID());

		mockMvc.perform(post("/workout/update/{id}", workoutId)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.session(session)
						.flashAttr("workout", inputDTO))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout"));

		verify(workoutService, times(1)).updateWorkout(any(), eq(inputDTO), any());
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "COACH"})
	void deleteWorkout() throws Exception {
		UUID workoutId = UUID.randomUUID();

		mockMvc.perform(post("/workout/delete/{id}", workoutId))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/workout"));

		verify(workoutService, times(1)).deleteWorkout(workoutId);
	}

}
