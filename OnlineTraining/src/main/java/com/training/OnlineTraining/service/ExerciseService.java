package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.input.ExerciseInputDTO;
import com.training.OnlineTraining.dto.output.ExerciseOutputDTO;
import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.enums.WorkoutType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface ExerciseService {

	ExerciseOutputDTO createExercise(ExerciseInputDTO exerciseInputDTO);

	ExerciseOutputDTO getExerciseById(UUID id);

	Page<ExerciseOutputDTO> getAllExercisesPageable(Pageable pageable);

	ExerciseOutputDTO updateExercise(UUID id, ExerciseInputDTO exerciseDetails);

	void deleteExercise(UUID id);

	void deleteAll();

	List<ExerciseOutputDTO> getAllExercises();

	List<Exercise> getAllExercisesForWorkoutType(WorkoutType workoutType);

	int countByWorkoutType(WorkoutType workoutType);

}