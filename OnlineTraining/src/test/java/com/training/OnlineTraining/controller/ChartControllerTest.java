package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ChartControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MeasurementService measurementService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void getPersonalMeasurementsAsc() throws Exception {
		UUID contractId = UUID.randomUUID();
		List<Measurement> measurements = Arrays.asList(new Measurement(), new Measurement());

		when(measurementService.getMeasurementsByContractIdSortedByDateAsc(contractId)).thenReturn(measurements);

		mockMvc.perform(get("/charts/{contractId}", contractId))
				.andExpect(status().isOk())
				.andExpect(view().name("client/chart"))
				.andExpect(model().attribute("measurements", measurements));
	}

}
