package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.dto.UserDto;

import java.util.List;
import java.util.UUID;


public interface AdminService {

	List<UserDto> getAllUsers();

	List<UserDto> filterUsersByRole(String role);

	void deleteUser(UUID userId);

	void updateUser(UUID userId, UserDto userDto);

	UserDto getUserById(UUID userId);

	List<ContractDto> getAllContracts();

	void updateContract(UUID contractId, ContractDto updatedContractDto);

	ContractDto getContractById(UUID contractId);

	void deleteContract(UUID contractId);

}
