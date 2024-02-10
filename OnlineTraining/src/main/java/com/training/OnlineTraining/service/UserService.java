package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.model.User;

import java.util.UUID;


public interface UserService {

	User registerUser(UserDTO request);

	User getUserById(UUID userId);

	int countUsers();

}