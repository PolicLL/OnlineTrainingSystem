package com.training.OnlineTraining.exceptions;

public class PasswordMismatchException extends RuntimeException {
	public PasswordMismatchException() {
		super("Passwords do not match");
	}
}
