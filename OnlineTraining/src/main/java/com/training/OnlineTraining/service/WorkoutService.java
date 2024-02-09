package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutDTO;

import java.util.List;
import java.util.UUID;


public interface WorkoutService {

	WorkoutDTO createEmptyWorkout(WorkoutDTO workoutDTO, UUID contractID);

	WorkoutDTO getWorkoutById(UUID id);

	List<WorkoutDTO> getAllWorkouts();

	WorkoutDTO updateWorkout(UUID id, WorkoutDTO workoutDetails);

	void incrementNumberOfExercises(UUID workoutID);

	void decrementNumberOfExercises(UUID workoutID);

	void updateWorkout(UUID id, WorkoutDTO workoutDetails, UUID contractID);

	void deleteWorkout(UUID id);

	List<WorkoutDTO> getWorkoutsByContractID(UUID contractID);

	WorkoutDTO createWorkoutUsingTemplate(WorkoutDTO workoutDTO, UUID contractID);

}
