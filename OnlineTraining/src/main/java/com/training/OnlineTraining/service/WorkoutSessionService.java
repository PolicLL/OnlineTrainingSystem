package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutSessionOutputDTO;
import com.training.OnlineTraining.model.WorkoutSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface WorkoutSessionService {

	WorkoutSessionOutputDTO createWorkoutSession(WorkoutSessionInputDTO workoutSessionInputDTO);

	WorkoutSessionOutputDTO getWorkoutSessionById(UUID id);

	List<WorkoutSessionOutputDTO> getAllWorkoutSessions();

	List<WorkoutSessionOutputDTO> getAllByWorkoutId(UUID workoutID);

	WorkoutSessionOutputDTO updateWorkoutSession(UUID id, WorkoutSessionInputDTO workoutSessionDetails);

	void updateWorkoutSessions(List<WorkoutSession> workoutSessionList);

	void deleteWorkoutSession(UUID id);

	void deleteAllWorkoutSessions();

	Optional<WorkoutSessionOutputDTO> getExerciseById(UUID workoutSessionId);

}
