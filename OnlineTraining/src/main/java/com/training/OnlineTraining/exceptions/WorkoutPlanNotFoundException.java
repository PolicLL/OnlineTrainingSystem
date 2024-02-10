package com.training.OnlineTraining.exceptions;

import java.util.UUID;


public class WorkoutPlanNotFoundException extends RuntimeException {

	public WorkoutPlanNotFoundException(UUID id) {

		super("Workout plan with ID " + id + " not found");
	}

}