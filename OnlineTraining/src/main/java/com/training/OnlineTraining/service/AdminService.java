package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.ContractDTO;
import com.training.OnlineTraining.dto.UserDTO;

import java.util.List;
import java.util.UUID;


public interface AdminService {

	List<UserDTO> getAllUsers();

	List<UserDTO> filterUsersByRole(String role);

	void deleteUser(UUID userId);

	void updateUser(UUID userId, UserDTO userDto);

	UserDTO getUserById(UUID userId);

	List<ContractDTO> getAllContracts();

	void updateContract(UUID contractId, ContractDTO updatedContractDTO);

	ContractDTO getContractById(UUID contractId);

	void deleteContract(UUID contractId);

}
