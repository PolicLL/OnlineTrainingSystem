package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
import com.training.OnlineTraining.model.Workout;

import java.util.List;
import java.util.UUID;


public interface WorkoutService {

	WorkoutOutputDTO createWorkout(WorkoutInputDTO workoutInputDTO, UUID contractID);

	WorkoutOutputDTO getWorkoutById(UUID id);

	List<WorkoutOutputDTO> getAllWorkouts();

	WorkoutOutputDTO updateWorkout(UUID id, WorkoutInputDTO workoutDetails);

	WorkoutOutputDTO updateWorkout(Workout workout);

	void incrementNumberOfExercises(UUID workoutID);

	void decrementNumberOfExercises(UUID workoutID);

	void updateWorkout(UUID id, WorkoutInputDTO workoutDetails, UUID contractID);

	void deleteWorkout(UUID id);

	void deleteAll();

	List<WorkoutOutputDTO> getWorkoutsByContractID(UUID contractID);

	WorkoutOutputDTO createWorkoutTemplate(WorkoutInputDTO workoutInputDTO, UUID contractID);

}
