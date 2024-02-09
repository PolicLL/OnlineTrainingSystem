package com.training.OnlineTraining.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class ClientDTO {

	private UUID id;
	private String medicalCondition;
	private String injuries;

}
