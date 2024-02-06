package com.training.OnlineTraining.exceptions;

import java.util.UUID;


public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(UUID id) {

		super("User with ID " + id + " not found");
	}

}
