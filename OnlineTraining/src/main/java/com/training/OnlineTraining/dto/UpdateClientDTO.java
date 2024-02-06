package com.training.OnlineTraining.dto;

import lombok.Data;


@Data
public class UpdateClientDTO {

	private String medicalCondition;
	private String injuries;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String country;
	private String phoneNumber;
	private String gender;
	private Integer age;

}
