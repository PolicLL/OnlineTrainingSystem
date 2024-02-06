package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.mapper.UserMapper;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.UserService;
import com.training.OnlineTraining.util.PasswordUtils;
import com.training.OnlineTraining.util.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public boolean areInputsInvalid(UserDto request) {

		return ValidationUtils.isStringNullOrEmpty(request.getFirstName()) ||
				ValidationUtils.isStringNullOrEmpty(request.getLastName()) ||
				ValidationUtils.isStringNullOrEmpty(request.getEmail()) ||
				ValidationUtils.isStringNullOrEmpty(request.getStreet()) ||
				ValidationUtils.isStringNullOrEmpty(request.getCity()) ||
				ValidationUtils.isStringNullOrEmpty(request.getCountry()) ||
				ValidationUtils.isStringNullOrEmpty(request.getPhoneNumber()) ||
				ValidationUtils.isStringNullOrEmpty(request.getGender()) ||
				ValidationUtils.isAgeInvalid(request.getAge()) ||
				ValidationUtils.isStringNullOrEmpty(request.getPassword());
	}

	@Override
	public User registerUser(UserDto request) {

		if (areInputsInvalid(request)) {
			throw new RuntimeException("Invalid user input");
		}

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Duplicate email");
		}

		var user = UserMapper.mapDtoToEntity(request);

		return userRepository.save(user);
	}

	@Override
	public User authenticate(String email, String enteredPassword) {

		return userRepository.findByEmail(email)
				.map(user -> {
					if (PasswordUtils.verifyPassword(enteredPassword, user.getPassword())) {
						return user;
					} else {
						throw new RuntimeException("Wrong password");
					}
				})
				.orElseThrow(() -> new RuntimeException("Authentication failed"));
	}

	public int countUsers() {

		return userRepository.countUsers();
	}

	@Override
	public User getUserById(UUID userId) {

		return userRepository.findById(userId).orElse(null);
	}

}
