package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutTemplateDTO;
import com.training.OnlineTraining.dto.WorkoutDTO;


public interface WorkoutTemplateCreatorService {

	WorkoutDTO createWorkoutInputDTO(WorkoutTemplateDTO workoutTemplateDTO);

}
