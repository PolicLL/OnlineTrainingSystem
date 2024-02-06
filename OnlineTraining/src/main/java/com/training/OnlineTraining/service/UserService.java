package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.User;

import java.util.UUID;


public interface UserService {

	boolean areInputsInvalid(UserDto request);

	User registerUser(UserDto request);

	User authenticate(String email, String enteredPassword);

	User getUserById(UUID userId);

	int countUsers();

}