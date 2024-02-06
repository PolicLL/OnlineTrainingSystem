package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.ClientDto;
import com.training.OnlineTraining.dto.UpdateClientDTO;
import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.service.ClientService;
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
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientService clientService;

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void getClientPage() throws Exception {
		mockMvc.perform(get("/clients/client-page"))
				.andExpect(status().isOk())
				.andExpect(view().name("client/client_page"));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void getBecomeClientPage() throws Exception {
		UUID userId = UUID.randomUUID();
		mockMvc.perform(get("/clients/register").param("userId", userId.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("client/client_register_page"))
				.andExpect(model().attribute("userId", userId))
				.andExpect(model().attribute("client", instanceOf(ClientDto.class)));
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void becomeClient() throws Exception {
		UUID userId = UUID.randomUUID();
		ClientDto clientDto = new ClientDto();

		mockMvc.perform(post("/clients/register")
						.param("userId", userId.toString())
						.flashAttr("clientDto", clientDto))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	@Test
	@WithMockUser(authorities = {"CLIENT"})
	void getSettings() throws Exception {
		UUID clientId = UUID.randomUUID();
		Client client = new Client();
		client.setId(clientId);
		client.setUser(new User());

		when(clientService.getClientsById(clientId)).thenReturn(client);

		mockMvc.perform(get("/clients/settings").sessionAttr("clientId", clientId))
				.andExpect(status().isOk())
				.andExpect(view().name("client/settings"))
				.andExpect(model().attribute("client", instanceOf(Client.class)))
				.andExpect(model().attribute("genderOptions", hasSize(2))); // Adjust the size based on your options
	}

	@Test
	@WithMockUser(authorities = {"ADMIN", "CLIENT"})
	void updateClient() throws Exception {
		UUID clientId = UUID.randomUUID();
		UpdateClientDTO updateClientDTO = new UpdateClientDTO(); // Add necessary attributes for the update DTO
		mockMvc.perform(put("/clients/update").sessionAttr("clientId", clientId)
						.flashAttr("updateClientDTO", updateClientDTO))
				.andExpect(view().name("index"));
	}



}
