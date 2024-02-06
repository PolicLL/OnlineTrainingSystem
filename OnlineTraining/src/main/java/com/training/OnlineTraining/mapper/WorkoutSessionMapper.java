package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutSessionOutputDTO;
import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.WorkoutSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Mapper(componentModel = "spring")
@Component
public interface WorkoutSessionMapper {

	WorkoutSessionMapper INSTANCE = Mappers.getMapper(WorkoutSessionMapper.class);

	@Mapping(source = "workoutId", target = "workout.id")
	@Mapping(target = "exercise", expression = "java(mapExercise(workoutSessionInputDTO.getExerciseId()))")
	WorkoutSession toWorkoutSession(WorkoutSessionInputDTO workoutSessionInputDTO);

	@Mapping(source = "workoutId", target = "workout.id")
	@Mapping(target = "exercise", expression = "java(mapExercise(workoutSessionOutputDTO.getExerciseId()))")
	WorkoutSession toWorkoutSession(WorkoutSessionOutputDTO workoutSessionOutputDTO);

	@Mapping(source = "workout.id", target = "workoutId")
	@Mapping(source = "exercise.id", target = "exerciseId")
	WorkoutSessionInputDTO toWorkoutSessionInputDTO(WorkoutSession workoutSession);

	@Mapping(source = "workout.id", target = "workoutId")
	@Mapping(source = "exercise.id", target = "exerciseId")
	WorkoutSessionOutputDTO toWorkoutSessionOutputDTO(WorkoutSession workoutSession);

	default Exercise mapExercise(UUID exerciseId) {

		if (exerciseId != null) {
			Exercise exercise = new Exercise();
			exercise.setId(exerciseId);
			return exercise;
		}
		return null;
	}


}
