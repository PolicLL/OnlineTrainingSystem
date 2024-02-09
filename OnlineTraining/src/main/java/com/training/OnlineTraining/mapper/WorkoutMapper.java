package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.WorkoutDTO;
import com.training.OnlineTraining.model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface WorkoutMapper {

	WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

	@Mapping(source = "contract.id", target = "contractId")
	WorkoutDTO toWorkoutDTO(Workout workout);

	@Mapping(source = "contractId", target = "contract.id")
	@Mapping(target = "workoutSessions", ignore = true)
	Workout toWorkout(WorkoutDTO workoutDTO);

}
