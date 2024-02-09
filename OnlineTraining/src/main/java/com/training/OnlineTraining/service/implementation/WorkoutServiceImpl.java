package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.WorkoutDTO;
import com.training.OnlineTraining.dto.WorkoutSessionDTO;
import com.training.OnlineTraining.exceptions.WorkoutNotFoundException;
import com.training.OnlineTraining.mapper.WorkoutMapper;
import com.training.OnlineTraining.mapper.WorkoutSessionMapper;
import com.training.OnlineTraining.model.Workout;
import com.training.OnlineTraining.repository.WorkoutRepository;
import com.training.OnlineTraining.repository.WorkoutSessionRepository;
import com.training.OnlineTraining.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

	private final WorkoutRepository workoutRepository;
	private WorkoutSessionRepository workoutSessionRepository;

	private final WorkoutMapper workoutMapper;
	private WorkoutSessionMapper workoutSessionMapper;

	private static final Logger logger = LoggerFactory.getLogger(WorkoutServiceImpl.class);

	@Override
	public WorkoutDTO createWorkoutUsingTemplate(WorkoutDTO workoutDTO, UUID contractID) {

		var savedWorkout = initializeWorkout(workoutDTO, contractID);

		createWorkoutSessions(workoutDTO, savedWorkout);

		logger.info("New workout created.");

		return workoutMapper.toWorkoutDTO(savedWorkout);
	}

	@Override
	public WorkoutDTO createEmptyWorkout(WorkoutDTO workoutDTO, UUID contractID) {

		var savedWorkout = initializeWorkout(workoutDTO, contractID);

		createEmptyWorkoutSessions(workoutDTO, savedWorkout);

		logger.info("New workout created.");

		return workoutMapper.toWorkoutDTO(savedWorkout);
	}

	private Workout initializeWorkout(WorkoutDTO workoutDTO, UUID contractID) {

		logger.info("Creating new workout.");

		workoutDTO.setDateOfWorkout(null);
		workoutDTO.setContractId(contractID);
		workoutDTO.setOrdinalNumberOfWorkout(getOrdinalNumberOfNextWorkout(contractID));

		return workoutRepository.save(workoutMapper.toWorkout(workoutDTO));
	}

	private void createEmptyWorkoutSessions(WorkoutDTO workoutDTO, Workout savedWorkout) {

		IntStream.range(0, workoutDTO.getNumberOfExercises())
				.mapToObj(i -> {
					WorkoutSessionDTO workoutSessionDTO = new WorkoutSessionDTO(savedWorkout);
					return workoutSessionMapper.toWorkoutSession(workoutSessionDTO);
				})
				.forEach(workoutSessionRepository::save);
	}

	private void createWorkoutSessions(WorkoutDTO workoutDTO, Workout savedWorkout) {

		workoutDTO.getWorkoutSessions().forEach(workoutSession -> workoutSession.setWorkout(savedWorkout));
		workoutSessionRepository.saveAll(workoutDTO.getWorkoutSessions());
	}

	private int getOrdinalNumberOfNextWorkout(UUID contractID) {

		var lastWorkout = workoutRepository.findTopByContractIdOrderByOrdinalNumberOfWorkoutDesc(contractID);
		return (lastWorkout != null) ? lastWorkout.getOrdinalNumberOfWorkout() + 1 : 1;
	}

	@Override
	public WorkoutDTO getWorkoutById(UUID id) {

		logger.info("Getting workout by ID: {}", id);

		return workoutMapper.toWorkoutDTO(requireWorkout(id));
	}

	private Workout requireWorkout(UUID id) {

		return workoutRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Workout with ID {} not found.", id);
					return new WorkoutNotFoundException(id);
				});
	}

	@Override
	public List<WorkoutDTO> getAllWorkouts() {

		logger.info("Getting all workouts.");

		return workoutRepository
				.findAll()
				.stream()
				.map(workoutMapper::toWorkoutDTO)
				.collect(Collectors.toList());
	}

	@Override
	public WorkoutDTO updateWorkout(UUID id, WorkoutDTO workoutDetails) {

		logger.info("Updating workout with ID: {}", id);

		var existingWorkout = requireWorkout(id);

		var updatedWorkout = workoutMapper.toWorkout(workoutDetails);
		updatedWorkout.setId(existingWorkout.getId()); // Ensure the ID is preserved

		return workoutMapper.toWorkoutDTO(workoutRepository.save(updatedWorkout));
	}

	@Override
	public void incrementNumberOfExercises(UUID workoutID) {

		WorkoutDTO tempWorkout = getWorkoutById(workoutID);
		tempWorkout.setNumberOfExercises(tempWorkout.getNumberOfExercises() + 1);
		workoutRepository.save(workoutMapper.toWorkout(tempWorkout));
	}

	@Override
	public void decrementNumberOfExercises(UUID workoutID) {

		WorkoutDTO tempWorkout = getWorkoutById(workoutID);
		tempWorkout.setNumberOfExercises(tempWorkout.getNumberOfExercises() - 1);
		workoutRepository.save(workoutMapper.toWorkout(tempWorkout));
	}

	@Override
	public void updateWorkout(UUID id, WorkoutDTO workoutDTO, UUID contractID) {

		logger.info("Updating workout with ID: {}", id);

		var existingWorkout = requireWorkout(id);

		workoutDTO.setContractId(contractID);
		var updatedWorkout = workoutMapper.toWorkout(workoutDTO);
		updatedWorkout.setId(existingWorkout.getId()); // Ensure the ID is preserved

		workoutRepository.save(updatedWorkout);
	}

	@Override
	public void deleteWorkout(UUID id) {

		logger.info("Deleting workout with ID: {}", id);

		workoutRepository.deleteById(id);
	}

	@Override
	public List<WorkoutDTO> getWorkoutsByContractID(UUID contractID) {

		logger.info("Getting workouts by Contract ID: {}", contractID);

		return workoutRepository
				.findByContractId(contractID)
				.stream()
				.map(workoutMapper::toWorkoutDTO)
				.collect(Collectors.toList());
	}

}
