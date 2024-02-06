package com.training.OnlineTraining.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class UpdateCoachDTO {

	private BigDecimal yearsOfExperience;
	private String education;
	private BigDecimal monthlyPrice;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String country;
	private String phoneNumber;
	private String gender;
	private Integer age;

}
