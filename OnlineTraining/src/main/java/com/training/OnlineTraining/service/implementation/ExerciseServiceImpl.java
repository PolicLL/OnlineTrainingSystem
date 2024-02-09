package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.ExerciseDTO;
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
	public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {

		logger.info("Creating new exercise.");

		Exercise exercise = exerciseMapper.toExercise(exerciseDTO);
		Exercise savedExercise = exerciseRepository.save(exercise);

		logger.info("New exercise created.");

		return exerciseMapper.toExerciseDTO(savedExercise);
	}

	@Override
	public ExerciseDTO getExerciseById(UUID id) {

		logger.info("Getting exercise by ID: {}", id);

		return exerciseMapper.toExerciseDTO(requireExercise(id));
	}

	private Exercise requireExercise(UUID id) {

		return exerciseRepository.findById(id)
				.orElseThrow(() -> {
					logger.error("Exercise with ID {} not found.", id);
					return new ExerciseNotFoundException("Exercise with ID " + id + " not found");
				});
	}

	@Override
	public Page<ExerciseDTO> getAllExercisesPageable(Pageable pageable) {

		logger.info("Getting all exercises (pageable).");

		return exerciseRepository.findAll(pageable)
				.map(exerciseMapper::toExerciseDTO);
	}


	@Override
	public List<ExerciseDTO> getAllExercises() {

		logger.info("Getting all exercises.");

		return exerciseRepository
				.findAll()
				.stream()
				.map(exerciseMapper::toExerciseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<Exercise> getAllExercisesForWorkoutType(WorkoutType workoutType) {

		return exerciseRepository
				.findAllByWorkoutType(workoutType);
	}

	@Override
	public int countByWorkoutType(WorkoutType workoutType) {

		return exerciseRepository.countByWorkoutType(workoutType);
	}


	@Override
	public ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDetails) {

		logger.info("Updating exercise with ID: {}", id);

		Exercise existingExercise = requireExercise(id);

		Exercise updatedExercise = exerciseMapper.toExercise(exerciseDetails);
		updatedExercise.setId(existingExercise.getId()); // Ensure the ID is preserved

		return exerciseMapper.toExerciseDTO(exerciseRepository.save(updatedExercise));

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
