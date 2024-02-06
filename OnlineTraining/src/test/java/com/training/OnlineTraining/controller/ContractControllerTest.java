package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.model.Contract;
import com.training.OnlineTraining.service.CoachService;
import com.training.OnlineTraining.service.ContractService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ContractControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContractService contractService;

	@MockBean
	private CoachService coachService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void viewContract() throws Exception {
		UUID coachId = UUID.randomUUID();

		UUID clientID = UUID.randomUUID();

		when(coachService.getMonthlyPriceById(coachId)).thenReturn(10.00);

		mockMvc.perform(get("/contracts/{coachId}", coachId).sessionAttr("clientId", clientID))
				.andExpect(status().isOk())
				.andExpect(view().name("client/contract_page"))
				.andExpect(model().attribute("coachId", coachId))
				.andExpect(model().attribute("clientId",clientID))
				.andExpect(model().attribute("contract", instanceOf(ContractDto.class)))
				.andExpect(model().attribute("monthlyPrice", 10.00));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void createContract() throws Exception {
		UUID coachId = UUID.randomUUID();
		ContractDto contractDto = new ContractDto(); // Add necessary attributes for the contract DTO

		Contract contract = new Contract();
		contract.setId(UUID.randomUUID());

		when(contractService.createContract(contractDto)).thenReturn(contract);

		mockMvc.perform(post("/contracts/{coachId}", coachId).flashAttr("contractDto", contractDto))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/measurements/" + contract.getId()));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void getPersonalContracts() throws Exception {
		UUID clientId = UUID.randomUUID();
		String clientName = "John Doe";
		mockMvc.perform(get("/contracts/personal").sessionAttr("clientId", clientId)
						.sessionAttr("clientName", clientName))
				.andExpect(status().isOk())
				.andExpect(view().name("client/personal_contracts"))
				.andExpect(model().attribute("contracts", instanceOf(List.class)))
				.andExpect(model().attribute("clientName", clientName));
	}

}
