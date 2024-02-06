package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutTemplate;
import com.training.OnlineTraining.dto.input.WorkoutInputDTO;


public interface WorkoutTemplateCreatorService {

	WorkoutInputDTO createWorkoutInputDTO(WorkoutTemplate workoutTemplate);

}
