package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutSessionDTO;
import com.training.OnlineTraining.model.WorkoutSession;

import java.util.List;
import java.util.UUID;


public interface WorkoutSessionService {

	WorkoutSessionDTO createWorkoutSession(WorkoutSessionDTO workoutSessionDTO);

	WorkoutSessionDTO getWorkoutSessionById(UUID id);

	List<WorkoutSessionDTO> getAllWorkoutSessions();

	List<WorkoutSessionDTO> getAllByWorkoutId(UUID workoutID);

	WorkoutSessionDTO updateWorkoutSession(UUID id, WorkoutSessionDTO workoutSessionDetails);

	void updateWorkoutSessions(List<WorkoutSession> workoutSessionList);

	void deleteWorkoutSession(UUID id);

	void deleteAllWorkoutSessions();

	WorkoutSessionDTO getExerciseById(UUID workoutSessionId);

}
