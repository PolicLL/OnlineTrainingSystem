package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.input.ExerciseInputDTO;
import com.training.OnlineTraining.dto.output.ExerciseOutputDTO;
import com.training.OnlineTraining.exceptions.ExerciseNotFoundException;
import com.training.OnlineTraining.mapper.ExerciseMapper;
import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.enums.WorkoutType;
import com.training.OnlineTraining.repository.ExerciseRepository;
import com.training.OnlineTraining.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;
	private final ExerciseMapper exerciseMapper;
	private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

	@Override
	public ExerciseOutputDTO createExercise(ExerciseInputDTO exerciseInputDTO) {

		logger.info("Creating new exercise.");

		Exercise exercise = exerciseMapper.toExercise(exerciseInputDTO);
		Exercise savedExercise = exerciseRepository.save(exercise);

		logger.info("New exercise created.");

		return exerciseMapper.toExerciseOutputDTO(savedExercise);
	}

	@Override
	public ExerciseOutputDTO getExerciseById(UUID id) {

		logger.info("Getting exercise by ID: {}", id);

		return exerciseMapper.toExerciseOutputDTO(requireExercise(id));
	}

	private Exercise requireExercise(UUID id) {

		return exerciseRepository.findById(id)
				.orElseThrow(() -> {
					logger.error("Exercise with ID {} not found.", id);
					return new ExerciseNotFoundException("Exercise with ID " + id + " not found");
				});
	}

	@Override
	public Page<ExerciseOutputDTO> getAllExercisesPageable(Pageable pageable) {

		logger.info("Getting all exercises (pageable).");

		return exerciseRepository.findAll(pageable)
				.map(exerciseMapper::toExerciseOutputDTO);
	}


	@Override
	public List<ExerciseOutputDTO> getAllExercises() {

		logger.info("Getting all exercises.");

		return exerciseRepository
				.findAll()
				.stream()
				.map(exerciseMapper::toExerciseOutputDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<Exercise> getAllExercisesForWorkoutType(WorkoutType workoutType) {

		return exerciseRepository.findAllByWorkoutType(workoutType);
	}

	@Override
	public int countByWorkoutType(WorkoutType workoutType) {

		return exerciseRepository.countByWorkoutType(workoutType);
	}


	@Override
	public ExerciseOutputDTO updateExercise(UUID id, ExerciseInputDTO exerciseDetails) {

		logger.info("Updating exercise with ID: {}", id);

		Exercise existingExercise = requireExercise(id);

		Exercise updatedExercise = exerciseMapper.toExercise(exerciseDetails);
		updatedExercise.setId(existingExercise.getId()); // Ensure the ID is preserved

		return exerciseMapper.toExerciseOutputDTO(exerciseRepository.save(updatedExercise));

	}

	@Override
	public void deleteExercise(UUID id) {

		logger.info("Deleting exercise with ID: {}", id);

		Exercise existingExercise = requireExercise(id);
		exerciseRepository.deleteById(existingExercise.getId());
	}

	@Override
	public void deleteAll() {

		logger.info("Deleting all exercises.");

		this.exerciseRepository.deleteAll();
	}

}
