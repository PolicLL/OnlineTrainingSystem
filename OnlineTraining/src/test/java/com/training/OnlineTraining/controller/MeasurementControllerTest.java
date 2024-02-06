package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class MeasurementControllerTest {


	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MeasurementService measurementService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void showMeasurementForm() throws Exception {
		UUID contractId = UUID.randomUUID();

		mockMvc.perform(get("/measurements/{contractId}", contractId))
				.andExpect(status().isOk())
				.andExpect(view().name("client/measurement_form"))
				.andExpect(model().attribute("contractId", contractId))
				.andExpect(model().attribute("measurementDto", new MeasurementDTO()));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void createMeasurement() throws Exception {
		UUID contractId = UUID.randomUUID();
		MeasurementDTO measurementDTO = new MeasurementDTO();

		mockMvc.perform(post("/measurements/{contractId}", contractId)
						.flashAttr("measurementDto", measurementDTO))
				.andExpect(status().isOk()) // Assuming "index" is a valid view, update as needed
				.andExpect(view().name("index"));

		verify(measurementService, times(1)).createMeasurement(any());
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void getPersonalMeasurements() throws Exception {
		UUID contractId = UUID.randomUUID();

		// Create a mock Page<Measurement>
		List<Measurement> measurementList = Arrays.asList(
				new Measurement(/* Set appropriate values */),
				new Measurement(/* Set appropriate values */)
		);

		Page<Measurement> measurementPage = new PageImpl<>(measurementList);

		// Mock the service call to return the mock Page<Measurement>
		when(measurementService.getMeasurementsByContractIdSortedByDatePageable(contractId, PageRequest.of(0, 10)))
				.thenReturn(measurementPage);

		mockMvc.perform(get("/measurements/personal/{contractId}", contractId)
						.param("page", "1")
						.param("size", "10"))
				.andExpect(status().isOk())
				.andExpect(view().name("client/personal_measurements"))
				.andExpect(model().attribute("measurements", measurementPage))
				.andExpect(model().attribute("currentPage", 1))
				.andExpect(model().attribute("totalPages", 1));
	}



	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void showUpdateForm() throws Exception {
		UUID contractId = UUID.randomUUID();
		UUID measurementId = UUID.randomUUID();
		MeasurementDTO measurementDTO = new MeasurementDTO();

		when(measurementService.getMeasurementById(measurementId)).thenReturn(measurementDTO);

		mockMvc.perform(get("/measurements/personal/{contractId}/update/{measurementId}", contractId, measurementId))
				.andExpect(status().isOk())
				.andExpect(view().name("client/update_measurement_form"))
				.andExpect(model().attribute("contractId", contractId))
				.andExpect(model().attribute("measurementId", measurementId))
				.andExpect(model().attribute("measurementDto", measurementDTO));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void updateMeasurement() throws Exception {
		UUID contractId = UUID.randomUUID();
		UUID measurementId = UUID.randomUUID();
		MeasurementDTO measurementDTO = new MeasurementDTO();
		measurementDTO.setContractId(contractId);

		mockMvc.perform(put("/measurements/personal/{contractId}/update/{measurementId}", contractId, measurementId)
						.flashAttr("measurementDto", measurementDTO))
				.andExpect(status().is3xxRedirection()) // Assuming a redirect after successful update
				.andExpect(redirectedUrl("/measurements/personal/" + contractId));

		verify(measurementService, times(1)).updateMeasurement(measurementId, measurementDTO);
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void deleteMeasurement() throws Exception {
		UUID contractId = UUID.randomUUID();
		UUID measurementId = UUID.randomUUID();

		mockMvc.perform(delete("/measurements/personal/{contractId}/delete/{measurementId}", contractId, measurementId))
				.andExpect(status().is3xxRedirection()) // Assuming a redirect after successful deletion
				.andExpect(redirectedUrl("/measurements/personal/" + contractId));

		verify(measurementService, times(1)).deleteMeasurement(measurementId);
	}
}
