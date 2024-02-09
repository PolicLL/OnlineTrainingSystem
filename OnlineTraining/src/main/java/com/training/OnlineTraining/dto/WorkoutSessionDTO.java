package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.Workout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@NoArgsConstructor
public class WorkoutSessionDTO {

	private UUID id;
	private UUID workoutId = null;
	private UUID exerciseId = null;
	private Integer numberOfReps = 0;
	private Integer pauseAfterExerciseInSeconds = 0;
	private BigDecimal weight = BigDecimal.valueOf(0.0);

	public WorkoutSessionDTO(Workout workout) {
		this.workoutId = workout.getId();
	}

	public WorkoutSessionDTO(UUID workoutID) {
		this.workoutId = workoutID;
	}

}
