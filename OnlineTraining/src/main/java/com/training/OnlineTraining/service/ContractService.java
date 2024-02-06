package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.model.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ContractService {

	Contract createContract(ContractDto contractDto);

	public List<Contract> getContractsByClientId(UUID clientId);

	Optional<Contract> getContractById(UUID id);

	List<Contract> getAllContracts();

	Contract updateContract(UUID id, ContractDto contractDetails);

	void deleteContract(UUID id);

	List<Contract> getAllContractsForCoach(UUID coachID);

	void deleteAllContracts();

}
