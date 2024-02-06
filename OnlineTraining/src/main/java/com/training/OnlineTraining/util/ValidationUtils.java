package com.training.OnlineTraining.util;

public class ValidationUtils {

	public static boolean isStringNullOrEmpty(String value) {

		return value == null || value.trim().isEmpty();
	}

	public static boolean isAgeInvalid(Integer age) {

		return age == null || age <= 0 || age >= 120;
	}

}
