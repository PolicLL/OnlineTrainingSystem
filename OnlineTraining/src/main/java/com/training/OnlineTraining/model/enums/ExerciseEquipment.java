package com.training.OnlineTraining.model.enums;

import lombok.Getter;


@Getter
public enum ExerciseEquipment {
	Barbell("Barbell"),
	Bench("Bench"),
	Squat_Rack("Squat Rack"),
	Weight_Plates("Weight Plates"),
	Pull_up_Bar("Pull-up Bar"),
	Dumbbells("Dumbbells"),
	None("None"),
	Leg_Press_Machine("Leg Press Machine"),
	Cable_Machine("Cable Machine"),
	Parallel_Bars("Parallel Bars"),
	Decline_Bench("Decline Bench"),
	Calf_Raise_Machine("Calf Raise Machine");

	private final String displayName;

	ExerciseEquipment(String displayName) {

		this.displayName = displayName;
	}

}