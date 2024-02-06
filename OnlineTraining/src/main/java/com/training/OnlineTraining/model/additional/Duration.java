package com.training.OnlineTraining.model.additional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class Duration {

	private int seconds;

	public int getDurationInSeconds() {

		return seconds;
	}

	public void add(int seconds) {

		this.seconds += seconds;
	}

	public void add(Duration duration) {

		this.seconds += duration.getDurationInSeconds();
	}

	@Override
	public String toString() {

		return getFormatedDuration();
	}


	public String getFormatedDuration() {

		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int remainingSeconds = seconds % 60;

		return String.format("%02dh:%02dm:%02ds", hours, minutes, remainingSeconds);
	}

}
