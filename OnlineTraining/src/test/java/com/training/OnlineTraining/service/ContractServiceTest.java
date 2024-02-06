package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.Contract;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.service.implementation.ClientServiceImpl;
import com.training.OnlineTraining.service.implementation.CoachServiceImpl;
import com.training.OnlineTraining.utils.TestDTOUtils;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class ContractServiceTest {

	@Autowired
	private ContractService contractService;

	@Autowired
	private ClientServiceImpl clientService;

	@Autowired
	private CoachServiceImpl coachService;
	@Autowired
	private UserService userService;


	private ContractDto contractDto;
	private Client client;
	private Coach coach;

	private int numberOfContractsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp() {
		numberOfContractsInDatabaseBeforeTest = contractService.getAllContracts().size();

		User newUser = userService.registerUser(TestDTOUtils.getUserDTO());
		User newUser2 = userService.registerUser(TestDTOUtils.getUserDTO());
		client = createSampleClient(newUser.getId());
		coach = createSampleCoach(newUser2.getId());

		contractDto = TestDTOUtils.getContractDTO(coach, client);
	}

	private Client createSampleClient(UUID userID) {
		return clientService.registerClient(TestDTOUtils.getClientDTO(), userID);
	}

	private Coach createSampleCoach(UUID userID) {
		return coachService.registerCoach(TestDTOUtils.getCoachDTO(), userID);
	}

	@Test
	public void testCreateContract() {
		Contract createdContract = contractService.createContract(contractDto);

		assertNotNull(createdContract);
		assertNotNull(createdContract.getId());
		assertEquals(client.getId(), createdContract.getClient().getId());
		assertEquals(coach.getId(), createdContract.getCoach().getId());

		List<Contract> contractsAfterTest = contractService.getAllContracts();
		assertEquals(numberOfContractsInDatabaseBeforeTest + 1, contractsAfterTest.size());
	}

	@Test
	public void testGetContractsByClientId() {
		Contract createdContract = contractService.createContract(contractDto);

		List<Contract> contractsByClientId = contractService.getContractsByClientId(client.getId());

		assertNotNull(contractsByClientId);

		assertTrue(contractsByClientId.contains(createdContract));
	}

	@Test
	public void testGetContractById() {
		Contract createdContract = contractService.createContract(contractDto);

		UUID contractId = createdContract.getId();

		Contract retrievedContract = contractService.getContractById(contractId).orElse(null);

		assertNotNull(retrievedContract);
		assertEquals(contractId, retrievedContract.getId());
	}

	@Test
	public void testUpdateContract() {

		Contract createdContract = contractService.createContract(contractDto);

		ContractDto updatedContractDto = TestDTOUtils.getContractDTO(coach, client);

		contractService.updateContract(createdContract.getId(), updatedContractDto);

		Contract updatedContract = contractService.getContractById(createdContract.getId()).orElse(null);

		assertNotNull(updatedContract);
		assertEquals(updatedContractDto.getMonthlyPrice(), updatedContract.getMonthlyPrice());
	}

	@Test
	public void testDeleteContract() {
		Contract createdContract = contractService.createContract(contractDto);

		UUID contractId = createdContract.getId();

		contractService.deleteContract(contractId);

		assertNull(contractService.getContractById(contractId).orElse(null));
	}

	@Test
	public void testGetAllContractsForCoach() {

		Contract createdContract = contractService.createContract(contractDto);

		List<Contract> contractsForCoach = contractService.getAllContractsForCoach(coach.getId());

		assertNotNull(contractsForCoach);
		assertTrue(contractsForCoach.contains(createdContract));
	}

}
