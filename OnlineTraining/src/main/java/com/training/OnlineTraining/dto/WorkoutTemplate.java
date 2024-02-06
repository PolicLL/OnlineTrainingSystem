package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.enums.WorkoutGoal;
import com.training.OnlineTraining.model.enums.WorkoutType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutTemplate {

	private WorkoutType workoutType;
	private WorkoutGoal workoutGoal;
	private int numberOfExercises;
	private WorkoutType nextWorkout;

}
