package com.training.OnlineTraining.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class WorkoutPlanDTO {

	private UUID id;
	private Date startDate;
	private Date endDate;
	private int numberOfWorkouts;
	private List<WorkoutDTO> workoutDTOList;

}

