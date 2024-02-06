package com.training.OnlineTraining.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExerciseNotFoundException extends RuntimeException {

	public ExerciseNotFoundException(String message) {

		super(message);
	}

	public ExerciseNotFoundException(String message, Throwable cause) {

		super(message, cause);
	}

}