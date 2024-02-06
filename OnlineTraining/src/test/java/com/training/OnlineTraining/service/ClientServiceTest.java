package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.ClientDto;
import com.training.OnlineTraining.dto.UpdateClientDTO;
import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.utils.TestDTOUtils;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class ClientServiceTest {

	@Autowired
	private ClientService clientService;

	@Autowired
	private UserService userService;

	private ClientDto clientDto;

	private UserDto userDto;

	private int numberOfClientsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp(){
		numberOfClientsInDatabaseBeforeTest = clientService.countClients();
		userDto = TestDTOUtils.getUserDTO();
		clientDto = TestDTOUtils.getClientDTO();
	}


	@Test
	public void testCreateClient() {
		User newUser = userService.registerUser(userDto);

		clientDto.setMedicalCondition("Medical Condition TEST");

		Client createdClient = clientService.registerClient(clientDto, newUser.getId());

		assertNotNull(createdClient);
		assertEquals("Medical Condition TEST", createdClient.getMedicalCondition());

	}

	@Test
	public void testGetClientByClientId() {
		User newUser = userService.registerUser(userDto);

		clientDto.setMedicalCondition("Medical Condition TEST");

		Client createdClient = clientService.registerClient(clientDto, newUser.getId());

		Client retrievedClient = clientService.getClientsById(createdClient.getId());

		assertNotNull(retrievedClient);
		assertEquals("Medical Condition TEST", retrievedClient.getMedicalCondition());

	}

	@Test
	public void testGetClientByUserId() {
		User newUser = userService.registerUser(userDto);

		clientDto.setMedicalCondition("Medical Condition TEST");

		clientService.registerClient(clientDto, newUser.getId());

		Client retrievedClient = clientService.getClientByUserId(newUser.getId());

		assertNotNull(retrievedClient);
		assertEquals("Medical Condition TEST", retrievedClient.getMedicalCondition());

	}

	@Test
	public void testUpdateClient() {
		User newUser = userService.registerUser(userDto);

		clientDto.setMedicalCondition("Medical Condition TEST");

		//

		Client createdClient = clientService.registerClient(clientDto, newUser.getId());

		UpdateClientDTO updateClientDto = TestDTOUtils.getUpdateClientDTO();
		updateClientDto.setMedicalCondition("MEDICAL Condition UPDATE");


		clientService.updateClient(createdClient.getId(), updateClientDto);

		//

		Client retrievedClient = clientService.getClientsById(createdClient.getId());

		assertNotNull(retrievedClient);
		assertEquals("MEDICAL Condition UPDATE", retrievedClient.getMedicalCondition());

	}




}






















