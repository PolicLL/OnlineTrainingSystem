package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.enums.Education;
import lombok.Data;

import java.util.UUID;


@Data
public class CoachDto {

	private UUID id;
	private CoachUserDTO coachUserDTO;
	private Double yearsOfExperience;
	private Education education;
	private Double monthlyPrice;

}
