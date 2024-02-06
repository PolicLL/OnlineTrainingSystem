package com.training.OnlineTraining.model.enums;

public enum WorkoutStatus {
	WAITING("Waiting"),
	SUCCESSFUL_WORKOUT("Successful workout"),
	NOT_FULLY_SUCCESSFUL_WORKOUT("Not fully successful workout"),
	SKIPPED_WORKOUT("Skipped workout");

	public final String displayName;

	WorkoutStatus(String displayName) {

		this.displayName = displayName;
	}

	public String getDisplayName() {

		return displayName;
	}
}
