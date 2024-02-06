package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.WorkoutTemplate;
import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.WorkoutSession;
import com.training.OnlineTraining.model.enums.WorkoutGoal;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import com.training.OnlineTraining.model.enums.WorkoutType;
import com.training.OnlineTraining.service.ExerciseService;
import com.training.OnlineTraining.service.WorkoutTemplateCreatorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class WorkoutTemplateCreatorServiceImpl implements WorkoutTemplateCreatorService {

	private static final Logger logger = LoggerFactory.getLogger(WorkoutTemplateCreatorServiceImpl.class);

	private final ExerciseService exerciseService;

	@Override
	public WorkoutInputDTO createWorkoutInputDTO(WorkoutTemplate workoutTemplate) {

		logger.info("Creating workout from template: {}", workoutTemplate);

		return WorkoutInputDTO.builder()
				.numberOfExercises(workoutTemplate.getNumberOfExercises())
				.warmingUpTimeInSeconds(300)
				.workoutStatus(WorkoutStatus.WAITING)
				.numberOfSets(getNumberOfSets(workoutTemplate.getWorkoutGoal()))
				.pauseBetweenSetsInSeconds(getPauseBetweenSetsInSeconds(workoutTemplate.getWorkoutGoal()))
				.workoutSessions(getWorkoutSessions(workoutTemplate))
				.nextWorkout(workoutTemplate.getNextWorkout())
				.build();
	}

	private int getNumberOfSets(WorkoutGoal workoutGoal) {

		int numberOfSets = switch (workoutGoal) {
			case STRENGTH -> 4;
			case ENDURANCE -> 8;
			case HYPERTROPHY -> 6;
		};
		logger.info("Calculated number of sets: {}", numberOfSets);
		return numberOfSets;
	}

	private int getPauseBetweenSetsInSeconds(WorkoutGoal workoutGoal) {

		int pauseInSeconds = switch (workoutGoal) {
			case STRENGTH -> 240;
			case ENDURANCE -> 120;
			case HYPERTROPHY -> 180;
		};
		logger.info("Calculated pause between sets in seconds: {}", pauseInSeconds);
		return pauseInSeconds;
	}

	private List<WorkoutSession> getWorkoutSessions(WorkoutTemplate workoutTemplate) {

		logger.info("Creating workout sessions for template: {}", workoutTemplate);

		var listOfExercisesForWorkoutType = getExercisesForWorkoutType(workoutTemplate.getWorkoutType(), workoutTemplate.getNumberOfExercises());

		return listOfExercisesForWorkoutType.stream()
				.map(exercise -> {
					logger.info("Creating workout session for exercise: {}", exercise);
					return WorkoutSession.builder()
							.exercise(exercise)
							.numberOfReps(getNumberOfReps(workoutTemplate.getWorkoutGoal()))
							.pauseAfterExerciseInSeconds(getRestAfterExerciseInSeconds(workoutTemplate.getWorkoutGoal()))
							.weight(getWeight(workoutTemplate.getWorkoutGoal()))
							.build();
				})
				.collect(Collectors.toList());
	}

	private List<Exercise> getExercisesForWorkoutType(WorkoutType workoutType, int numberOfExercises) {

		logger.info("Getting {} exercises for workout type: {}", numberOfExercises, workoutType);

		var listOfExercisesForWorkoutType = exerciseService.getAllExercisesForWorkoutType(workoutType);
		Collections.shuffle(listOfExercisesForWorkoutType);

		var selectedExercises = listOfExercisesForWorkoutType.subList(0, numberOfExercises);
		logger.info("Selected exercises: {}", selectedExercises);

		return selectedExercises;
	}

	private int getNumberOfReps(WorkoutGoal workoutGoal) {

		int numberOfReps = switch (workoutGoal) {
			case STRENGTH -> 5;
			case ENDURANCE -> 20;
			case HYPERTROPHY -> 12;
		};
		logger.info("Calculated number of reps: {}", numberOfReps);
		return numberOfReps;
	}

	private int getRestAfterExerciseInSeconds(WorkoutGoal workoutGoal) {

		int restInSeconds = switch (workoutGoal) {
			case STRENGTH -> 120;
			case ENDURANCE -> 20;
			case HYPERTROPHY -> 60;
		};
		logger.info("Calculated rest after exercise in seconds: {}", restInSeconds);
		return restInSeconds;
	}

	private BigDecimal getWeight(WorkoutGoal workoutGoal) {

		BigDecimal weight = switch (workoutGoal) {
			case STRENGTH -> BigDecimal.valueOf(100.00);
			case ENDURANCE -> BigDecimal.valueOf(30.00);
			case HYPERTROPHY -> BigDecimal.valueOf(80.00);
		};
		logger.info("Calculated weight: {}", weight);
		return weight;
	}

}
