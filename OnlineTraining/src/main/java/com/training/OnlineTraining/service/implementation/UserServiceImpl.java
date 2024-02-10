package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.mapper.UserMapper;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.MailService;
import com.training.OnlineTraining.service.UserService;
import com.training.OnlineTraining.util.ValidationUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MailService mailService;

	@Override
	public User registerUser(UserDTO request) {

		checkIfUserCanBeRegistered(request);

		var user = saveUserToDatabase(request);

		sendWelcomeMail(user);

		return user;
	}

	private void checkIfUserCanBeRegistered(UserDTO request) {

		if (areInputsInvalid(request))
			throw new RuntimeException("Invalid user input");

		if (isEmailAlreadyUsed(request))
			throw new RuntimeException("Duplicate email");
	}

	private boolean areInputsInvalid(UserDTO request) {

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

	private User saveUserToDatabase(UserDTO request){
		var user = UserMapper.mapDtoToEntity(request);
		return userRepository.save(user);
	}

	private boolean isEmailAlreadyUsed(UserDTO request) {

		return userRepository.findByEmail(request.getEmail()).isPresent();
	}

	private void sendWelcomeMail(User user) {

		try {
			mailService.sendEmailAfterRegistration(user.getEmail());
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public int countUsers() {

		return userRepository.countUsers();
	}

	@Override
	public User getUserById(UUID userId) {

		return userRepository.findById(userId).orElse(null);
	}

}
