package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
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
	WorkoutInputDTO toWorkoutInputDTO(Workout workout);

	@Mapping(source = "contract.id", target = "contractId")
	WorkoutOutputDTO toWorkoutOutputDTO(Workout workout);

	@Mapping(source = "contractId", target = "contract.id")
	Workout toWorkout(WorkoutInputDTO workoutInputDTO);

	@Mapping(source = "contractId", target = "contract.id")
	Workout toWorkout(WorkoutOutputDTO workoutOutputDTO);

}
