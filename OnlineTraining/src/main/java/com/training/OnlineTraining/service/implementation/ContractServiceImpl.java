package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.exceptions.ContractNotFoundException;
import com.training.OnlineTraining.mapper.ContractMapper;
import com.training.OnlineTraining.model.Contract;
import com.training.OnlineTraining.repository.ContractRepository;
import com.training.OnlineTraining.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {

	private final ContractRepository contractRepository;
	private final ContractMapper contractMapper;

	@Override
	public Contract createContract(ContractDto contractDto) {

		var contract = contractMapper.mapDto(contractDto);
		return contractRepository.save(contract);
	}

	@Override
	public List<Contract> getContractsByClientId(UUID clientId) {

		return contractRepository.findByClientId(clientId);
	}


	@Override
	public Optional<Contract> getContractById(UUID id) {

		return contractRepository.findById(id);
	}

	@Override
	public List<Contract> getAllContracts() {

		return contractRepository.findAll();
	}

	@Override
	public Contract updateContract(UUID id, ContractDto contractDetails) {

		return contractRepository.findById(id)
				.map(contract -> {
					Contract updatedContract = contractMapper.mapDto(contractDetails);
					updatedContract.setId(contract.getId()); // Set the ID of the existing contract
					return contractRepository.save(updatedContract);
				})
				.orElseThrow(() -> new ContractNotFoundException("Contract with ID " + id + " not found"));
	}

	@Override
	public void deleteContract(UUID id) {

		if (contractRepository.existsById(id)) {
			contractRepository.deleteById(id);
		} else {
			throw new ContractNotFoundException("Contract with ID " + id + " not found");
		}
	}

	@Override
	public List<Contract> getAllContractsForCoach(UUID coachID) {

		return contractRepository.findByCoachId(coachID);
	}

	@Override
	public void deleteAllContracts() {

		contractRepository.deleteAll();
	}

}
