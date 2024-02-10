package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.WorkoutSession;
import com.training.OnlineTraining.model.additional.Duration;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import com.training.OnlineTraining.model.enums.WorkoutType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDTO {

	private UUID id;

	private UUID contractId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfWorkout;

	private Integer ordinalNumberOfWorkout;
	private Integer numberOfExercises;
	private Integer warmingUpTimeInSeconds;
	private Integer numberOfSets;
	private Integer pauseBetweenSetsInSeconds;
	private Integer selfRating;
	private Boolean isFinished;
	private WorkoutStatus workoutStatus = WorkoutStatus.WAITING;

	private List<WorkoutSession> workoutSessions;

	private WorkoutType nextWorkout;

	public WorkoutDTO(Integer numberOfExercises, Integer warmingUpTimeInSeconds, Integer numberOfSets, Integer pauseBetweenSetsInSeconds, List<WorkoutSession> workoutSessions) {

		this.numberOfExercises = numberOfExercises;
		this.warmingUpTimeInSeconds = warmingUpTimeInSeconds;
		this.numberOfSets = numberOfSets;
		this.pauseBetweenSetsInSeconds = pauseBetweenSetsInSeconds;
		this.workoutSessions = workoutSessions;
	}

	public WorkoutDTO(UUID contractId) {

		this.contractId = contractId;
	}

	@Override
	public String toString() {

		StringBuilder output = new StringBuilder("WorkoutInputDTO{" +
				"contractId=" + contractId +
				", dateOfWorkout=" + dateOfWorkout +
				", ordinalNumberOfWorkout=" + ordinalNumberOfWorkout +
				", numberOfExercises=" + numberOfExercises +
				", warmingUpTimeInSeconds=" + warmingUpTimeInSeconds +
				", numberOfSets=" + numberOfSets +
				", pauseBetweenSetsInSeconds=" + pauseBetweenSetsInSeconds +
				", selfRating=" + selfRating +
				", isFinished=" + isFinished +
				", workoutStatus=" + workoutStatus + "\n");

		if (workoutSessions != null)
			workoutSessions.forEach(output::append);

		return output.toString();
	}

	public Duration getDuration() {

		Duration newDuration = new Duration();

		newDuration.add(warmingUpTimeInSeconds);
		newDuration.add((numberOfSets - 1) * pauseBetweenSetsInSeconds);

		for (WorkoutSession workoutSession : workoutSessions)
			newDuration.add(workoutSession.getDuration().getDurationInSeconds() * numberOfSets);

		return newDuration;
	}

}
