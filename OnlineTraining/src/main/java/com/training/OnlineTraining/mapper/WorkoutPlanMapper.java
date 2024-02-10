package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.WorkoutPlanDTO;
import com.training.OnlineTraining.model.WorkoutPlan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface WorkoutPlanMapper {

	WorkoutPlanMapper INSTANCE = Mappers.getMapper(WorkoutPlanMapper.class);

	WorkoutPlanDTO toWorkoutPlanDTO(WorkoutPlan workoutPlan);

	WorkoutPlan toWorkoutPlan(WorkoutPlanDTO workoutPlanDTO);

}
