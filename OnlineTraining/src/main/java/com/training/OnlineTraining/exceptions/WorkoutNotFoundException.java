package com.training.OnlineTraining.exceptions;

import java.util.UUID;


public class WorkoutNotFoundException extends RuntimeException {

	public WorkoutNotFoundException(UUID id) {

		super("Workout with ID " + id + " not found");
	}

}