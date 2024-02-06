package com.training.OnlineTraining.dto.input;

import com.training.OnlineTraining.model.Workout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@NoArgsConstructor
public class WorkoutSessionInputDTO {

	private UUID workoutId = null;
	private UUID exerciseId = null;
	private Integer numberOfReps = 0;
	private Integer pauseAfterExerciseInSeconds = 0;
	private BigDecimal weight = BigDecimal.valueOf(0.0);

	public WorkoutSessionInputDTO(Workout workout) {

		this.workoutId = workout.getId();
	}

	public WorkoutSessionInputDTO(UUID workoutID) {

		this.workoutId = workoutID;
	}

}
