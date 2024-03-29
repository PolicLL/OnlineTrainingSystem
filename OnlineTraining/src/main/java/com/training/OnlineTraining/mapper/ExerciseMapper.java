package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.ExerciseDTO;
import com.training.OnlineTraining.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface ExerciseMapper {

	ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

	Exercise toExercise(ExerciseDTO exerciseDTO);

	ExerciseDTO toExerciseDTO(Exercise exercise);

}