package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.ExerciseDTO;
import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.enums.WorkoutType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface ExerciseService {

	ExerciseDTO createExercise(ExerciseDTO exerciseDTO);

	ExerciseDTO getExerciseById(UUID id);

	Page<ExerciseDTO> getAllExercisesPageable(Pageable pageable);

	ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDetails);

	void deleteExercise(UUID id);

	void deleteAll();

	List<ExerciseDTO> getAllExercises();

	List<Exercise> getAllExercisesForWorkoutType(WorkoutType workoutType);

	int countByWorkoutType(WorkoutType workoutType);

}