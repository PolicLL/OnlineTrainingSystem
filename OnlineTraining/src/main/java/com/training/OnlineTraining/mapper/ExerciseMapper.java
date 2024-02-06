package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.input.ExerciseInputDTO;
import com.training.OnlineTraining.dto.output.ExerciseOutputDTO;
import com.training.OnlineTraining.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface ExerciseMapper {

	ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

	Exercise toExercise(ExerciseInputDTO exerciseInputDTO);

	ExerciseInputDTO toExerciseInputDTO(Exercise exercise);

	ExerciseOutputDTO toExerciseOutputDTO(Exercise exercise);

}