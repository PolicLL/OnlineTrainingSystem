package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.ContractDTO;
import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.mapper.ContractMapper;
import com.training.OnlineTraining.mapper.UserAdminMapper;
import com.training.OnlineTraining.mapper.UserMapper;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.model.enums.Role;
import com.training.OnlineTraining.repository.ContractRepository;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.AdminService;
import com.training.OnlineTraining.specification.UserSpecifications;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserAdminMapper userAdminMapper;
	private final ContractMapper contractMapper;
	private final ContractRepository contractRepository;

	@Override
	public List<UserDTO> getUsers(String roleFilter){
		return StringUtils.isEmpty(roleFilter) ? getAllUsers() :
				filterUsersByRole(String.valueOf(Role.valueOf(roleFilter)));
	}

	@Override
	public List<UserDTO> getAllUsers() {

		return userRepository.findAll().stream()
				.map(userMapper::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> filterUsersByRole(String role) {

		Specification<User> spec = UserSpecifications.filterByRole(role);

		Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
		var filteredUsers = userRepository.findAll(spec, sort);

		return filteredUsers.stream()
				.map(userMapper::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteUser(UUID userId) {

		userRepository.deleteById(userId);
	}


	@Override
	public void updateUser(UUID userId, UserDTO userDto) {

		var user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

		userAdminMapper.updateEntityFromDto(userDto, user);
		userRepository.save(user);
	}

	@Override
	public UserDTO getUserById(UUID userId) {

		var user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

		return userMapper.convertToDto(user);
	}

	@Override
	public List<ContractDTO> getAllContracts() {

		var contracts = contractRepository.findAll();
		return contractMapper.mapToDtoList(contracts);
	}

	@Override
	public void updateContract(UUID contractId, ContractDTO updatedContractDTO) {

		var existingContract = contractRepository.findById(contractId)
				.orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));

		var updatedContract = contractMapper.mapDto(updatedContractDTO);

		contractRepository.save(updatedContract);
	}

	@Override
	public ContractDTO getContractById(UUID contractId) {

		var contract = contractRepository.findById(contractId)
				.orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
		return contractMapper.mapToDto(contract);
	}

	@Override
	public void deleteContract(UUID contractId) {

		contractRepository.deleteById(contractId);
	}


}
