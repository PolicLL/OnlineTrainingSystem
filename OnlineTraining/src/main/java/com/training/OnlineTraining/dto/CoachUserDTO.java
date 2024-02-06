package com.training.OnlineTraining.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class CoachUserDTO {

	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String street;
	private String city;
	private String country;
	private String phoneNumber;
	private String gender;
	private Integer age;

}