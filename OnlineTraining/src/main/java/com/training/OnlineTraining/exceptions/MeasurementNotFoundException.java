package com.training.OnlineTraining.exceptions;

import java.util.UUID;


public class MeasurementNotFoundException extends RuntimeException {

	public MeasurementNotFoundException(UUID id) {

		super("Measurement with ID " + id + " not found");
	}

}