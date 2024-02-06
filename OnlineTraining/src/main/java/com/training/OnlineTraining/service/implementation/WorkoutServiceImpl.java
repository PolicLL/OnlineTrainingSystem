package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
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
	private final WorkoutMapper workoutMapper;

	private WorkoutSessionRepository workoutSessionRepository;
	private WorkoutSessionMapper workoutSessionMapper;

	private static final Logger logger = LoggerFactory.getLogger(WorkoutServiceImpl.class);

	@Override
	public WorkoutOutputDTO createWorkoutTemplate(WorkoutInputDTO workoutInputDTO, UUID contractID) {

		logger.info("Creating new workout.");

		workoutInputDTO.setDateOfWorkout(null);
		workoutInputDTO.setContractId(contractID);
		workoutInputDTO.setOrdinalNumberOfWorkout(getOrdinalNumberOfNextWorkout(contractID));

		var savedWorkout = workoutRepository.save(workoutMapper.toWorkout(workoutInputDTO));

		if (savedWorkout.getWorkoutSessions() == null)
			createEmptyWorkoutSessions(workoutInputDTO, savedWorkout);

		else
			updateCreatedWorkoutSessions(savedWorkout);

		logger.info("New workout created.");

		return workoutMapper.toWorkoutOutputDTO(savedWorkout);
	}

	@Override
	public WorkoutOutputDTO createWorkout(WorkoutInputDTO workoutInputDTO, UUID contractID) {

		logger.info("Creating new workout.");

		workoutInputDTO.setDateOfWorkout(null);
		workoutInputDTO.setContractId(contractID);
		workoutInputDTO.setOrdinalNumberOfWorkout(getOrdinalNumberOfNextWorkout(contractID));

		var savedWorkout = workoutRepository.save(workoutMapper.toWorkout(workoutInputDTO));

		IntStream.range(0, workoutInputDTO.getNumberOfExercises())
				.mapToObj(i -> {
					WorkoutSessionInputDTO workoutSessionInputDTO = new WorkoutSessionInputDTO(savedWorkout);
					return workoutSessionMapper.toWorkoutSession(workoutSessionInputDTO);
				})
				.forEach(workoutSessionRepository::save);

		logger.info("New workout created.");

		return workoutMapper.toWorkoutOutputDTO(savedWorkout);
	}

	private void createEmptyWorkoutSessions(WorkoutInputDTO workoutInputDTO, Workout savedWorkout) {

		IntStream.range(0, workoutInputDTO.getNumberOfExercises())
				.mapToObj(i -> {
					WorkoutSessionInputDTO workoutSessionInputDTO = new WorkoutSessionInputDTO(savedWorkout);
					return workoutSessionMapper.toWorkoutSession(workoutSessionInputDTO);
				})
				.forEach(workoutSessionRepository::save);
	}

	private void updateCreatedWorkoutSessions(Workout savedWorkout) {

		savedWorkout.getWorkoutSessions().forEach(workoutSession -> workoutSession.setWorkout(savedWorkout));
		workoutSessionRepository.saveAll(savedWorkout.getWorkoutSessions());
	}

	private int getOrdinalNumberOfNextWorkout(UUID contractID) {

		var lastWorkout = workoutRepository.findTopByContractIdOrderByOrdinalNumberOfWorkoutDesc(contractID);
		return (lastWorkout != null) ? lastWorkout.getOrdinalNumberOfWorkout() + 1 : 1;
	}

	@Override
	public WorkoutOutputDTO getWorkoutById(UUID id) {

		logger.info("Getting workout by ID: {}", id);

		return workoutMapper.toWorkoutOutputDTO(requireWorkout(id));
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
	public List<WorkoutOutputDTO> getAllWorkouts() {

		logger.info("Getting all workouts.");

		return workoutRepository
				.findAll()
				.stream()
				.map(workoutMapper::toWorkoutOutputDTO)
				.collect(Collectors.toList());
	}

	@Override
	public WorkoutOutputDTO updateWorkout(UUID id, WorkoutInputDTO workoutDetails) {

		logger.info("Updating workout with ID: {}", id);

		var existingWorkout = requireWorkout(id);

		var updatedWorkout = workoutMapper.toWorkout(workoutDetails);
		updatedWorkout.setId(existingWorkout.getId()); // Ensure the ID is preserved

		return workoutMapper.toWorkoutOutputDTO(workoutRepository.save(updatedWorkout));
	}

	@Override
	public WorkoutOutputDTO updateWorkout(Workout workout) {

		return workoutMapper.toWorkoutOutputDTO(workoutRepository.save(workout));
	}

	@Override
	public void incrementNumberOfExercises(UUID workoutID) {

		WorkoutOutputDTO tempWorkout = getWorkoutById(workoutID);
		tempWorkout.setNumberOfExercises(tempWorkout.getNumberOfExercises() + 1);
		workoutRepository.save(workoutMapper.toWorkout(tempWorkout));
	}

	@Override
	public void decrementNumberOfExercises(UUID workoutID) {

		WorkoutOutputDTO tempWorkout = getWorkoutById(workoutID);
		tempWorkout.setNumberOfExercises(tempWorkout.getNumberOfExercises() - 1);
		workoutRepository.save(workoutMapper.toWorkout(tempWorkout));
	}

	@Override
	public void updateWorkout(UUID id, WorkoutInputDTO workoutInputDTO, UUID contractID) {

		logger.info("Updating workout with ID: {}", id);

		var existingWorkout = requireWorkout(id);

		workoutInputDTO.setContractId(contractID);
		var updatedWorkout = workoutMapper.toWorkout(workoutInputDTO);
		updatedWorkout.setId(existingWorkout.getId()); // Ensure the ID is preserved

		workoutRepository.save(updatedWorkout);
	}

	@Override
	public void deleteWorkout(UUID id) {

		logger.info("Deleting workout with ID: {}", id);

		workoutRepository.deleteById(id);
	}

	@Override
	public void deleteAll() {

		logger.info("Deleting all workouts.");

		workoutRepository.deleteAll();
	}

	@Override
	public List<WorkoutOutputDTO> getWorkoutsByContractID(UUID contractID) {

		logger.info("Getting workouts by Contract ID: {}", contractID);

		return workoutRepository
				.findByContractId(contractID)
				.stream()
				.map(workoutMapper::toWorkoutOutputDTO)
				.collect(Collectors.toList());
	}

}
