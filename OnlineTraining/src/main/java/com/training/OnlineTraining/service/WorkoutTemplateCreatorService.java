package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.WorkoutTemplateDTO;
import com.training.OnlineTraining.dto.input.WorkoutInputDTO;


public interface WorkoutTemplateCreatorService {

	WorkoutInputDTO createWorkoutInputDTO(WorkoutTemplateDTO workoutTemplateDTO);

}
