package com.training.OnlineTraining.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkoutSessionNotFoundException extends RuntimeException {

	public WorkoutSessionNotFoundException(UUID id) {

		super("Workout session not found with ID: " + id);
	}

}
