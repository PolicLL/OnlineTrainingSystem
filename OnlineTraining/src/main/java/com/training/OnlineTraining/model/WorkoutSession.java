package com.training.OnlineTraining.model;

import com.training.OnlineTraining.model.additional.Duration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static com.training.OnlineTraining.util.WorkoutConstants.DURATION_OF_ONE_REP_IN_SECONDS;


@Entity
@Data
@Table(name = "workout_session")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkoutSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "workout_id", referencedColumnName = "id", nullable = false)
	private Workout workout;

	@ManyToOne
	@JoinColumn(name = "exercise_id", referencedColumnName = "id", nullable = true)
	private Exercise exercise;

	@Column(name = "number_of_reps")
	private Integer numberOfReps;

	@Column(name = "pause_after_exercise_in_seconds")
	private Integer pauseAfterExerciseInSeconds;

	@Column
	private BigDecimal weight;

	public Duration getDuration() {

		Duration newDuration = new Duration();

		newDuration.add(this.numberOfReps * DURATION_OF_ONE_REP_IN_SECONDS);
		newDuration.add(pauseAfterExerciseInSeconds);

		return newDuration;
	}

	@Override
	public String toString() {

		return "WorkoutSession{" +
				"id=" + id +
				", numberOfReps=" + numberOfReps +
				", exercise =" + exercise +
				", pauseAfterExerciseInSeconds=" + pauseAfterExerciseInSeconds +
				", weight=" + weight +
				'}' + '\n';
	}

}
