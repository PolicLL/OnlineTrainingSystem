package com.training.OnlineTraining.model.enums;

public enum Education {
	HIGH_SCHOOL("High School"),
	BACHELORS("Bachelor's"),
	MASTERS("Master's"),
	PHD("PhD"),
	CERTIFIED_PERSONAL_TRAINER("Certified Personal Trainer");

	private final String displayName;

	Education(String displayName) {

		this.displayName = displayName;
	}

	public String getDisplayName() {

		return displayName;
	}
}
