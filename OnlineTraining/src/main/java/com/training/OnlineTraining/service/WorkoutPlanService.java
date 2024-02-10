package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutPlanDTO;

import java.util.List;
import java.util.UUID;


public interface WorkoutPlanService {

	WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO);

	WorkoutPlanDTO getWorkoutPlanById(UUID workoutPlanID);

	List<WorkoutPlanDTO> getAllWorkoutPlans();

	WorkoutPlanDTO updateWorkoutPlan(UUID workoutPlanID, WorkoutPlanDTO workoutPlanDTO);

	void deleteWorkoutPlan(UUID workoutPlanID);

}
