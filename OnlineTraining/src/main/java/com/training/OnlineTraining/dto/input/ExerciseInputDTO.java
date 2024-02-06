package com.training.OnlineTraining.dto.input;

import com.training.OnlineTraining.model.enums.ExerciseDifficultyLevel;
import com.training.OnlineTraining.model.enums.ExerciseEquipment;
import com.training.OnlineTraining.model.enums.WorkoutType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ExerciseInputDTO {

	private String name;
	private String description;

	private ExerciseEquipment exerciseEquipment;

	private ExerciseDifficultyLevel exerciseDifficultyLevel;

	private WorkoutType workoutType;

	public ExerciseInputDTO(String name, String description, ExerciseEquipment exerciseEquipment, ExerciseDifficultyLevel exerciseDifficultyLevel) {

		this.name = name;
		this.description = description;
		this.exerciseEquipment = exerciseEquipment;
		this.exerciseDifficultyLevel = exerciseDifficultyLevel;
	}

}