package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.model.User;

import java.util.UUID;


public interface UserService {

	boolean areInputsInvalid(UserDTO request);

	User registerUser(UserDTO request);

	User authenticate(String email, String enteredPassword);

	User getUserById(UUID userId);

	int countUsers();

}