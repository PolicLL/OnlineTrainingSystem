package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
import com.training.OnlineTraining.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

	@Mock
	private WorkoutService workoutService;

	@InjectMocks
	private ReportController reportController;

	private MockMvc mockMvc;

	@Test
	void showPdf() throws Exception {
		UUID workoutId = UUID.randomUUID();
		WorkoutOutputDTO workoutOutputDTO = new WorkoutOutputDTO();
		when(workoutService.getWorkoutById(any(UUID.class))).thenReturn(workoutOutputDTO);

		this.mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();

		mockMvc.perform(get("/report/show")
						.param("id", workoutId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("report/workout_pdf_template"))
				.andExpect(model().attribute("workout", workoutOutputDTO));
	}

}
