package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;

import java.util.List;
import java.util.UUID;


public interface WorkoutService {

	WorkoutOutputDTO createEmptyWorkout(WorkoutInputDTO workoutInputDTO, UUID contractID);

	WorkoutOutputDTO getWorkoutById(UUID id);

	List<WorkoutOutputDTO> getAllWorkouts();

	WorkoutOutputDTO updateWorkout(UUID id, WorkoutInputDTO workoutDetails);

	void incrementNumberOfExercises(UUID workoutID);

	void decrementNumberOfExercises(UUID workoutID);

	void updateWorkout(UUID id, WorkoutInputDTO workoutDetails, UUID contractID);

	void deleteWorkout(UUID id);

	List<WorkoutOutputDTO> getWorkoutsByContractID(UUID contractID);

	WorkoutOutputDTO createWorkoutUsingTemplate(WorkoutInputDTO workoutInputDTO, UUID contractID);

}
