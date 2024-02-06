package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutSessionOutputDTO;
import com.training.OnlineTraining.exceptions.WorkoutSessionNotFoundException;
import com.training.OnlineTraining.mapper.WorkoutSessionMapper;
import com.training.OnlineTraining.model.WorkoutSession;
import com.training.OnlineTraining.repository.WorkoutSessionRepository;
import com.training.OnlineTraining.service.WorkoutService;
import com.training.OnlineTraining.service.WorkoutSessionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

	private final WorkoutSessionRepository workoutSessionRepository;
	private final WorkoutSessionMapper workoutSessionMapper;
	private WorkoutService workoutService;
	private static final Logger logger = LoggerFactory.getLogger(WorkoutSessionServiceImpl.class);

	@Autowired
	public void setWorkoutService(WorkoutService workoutService) {

		this.workoutService = workoutService;
	}


	@Override
	public WorkoutSessionOutputDTO createWorkoutSession(WorkoutSessionInputDTO workoutSessionInputDTO) {

		logger.info("Creating new workout session.");

		var workoutSession = workoutSessionMapper.toWorkoutSession(workoutSessionInputDTO);
		var savedWorkoutSession = workoutSessionRepository.save(workoutSession);

		workoutService.incrementNumberOfExercises(workoutSessionInputDTO.getWorkoutId());

		logger.info("New workout session created.");

		return workoutSessionMapper.toWorkoutSessionOutputDTO(savedWorkoutSession);
	}

	@Override
	public WorkoutSessionOutputDTO getWorkoutSessionById(UUID id) {

		logger.info("Getting workout session by ID: {}", id);

		return workoutSessionMapper.toWorkoutSessionOutputDTO(requireWorkoutSession(id));
	}

	private WorkoutSession requireWorkoutSession(UUID id) {

		return workoutSessionRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Workout session with ID {} not found.", id);
					return new WorkoutSessionNotFoundException(id);
				});
	}

	@Override
	public List<WorkoutSessionOutputDTO> getAllWorkoutSessions() {

		logger.info("Getting all workout sessions.");

		return workoutSessionRepository
				.findAll()
				.stream()
				.map(workoutSessionMapper::toWorkoutSessionOutputDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkoutSessionOutputDTO> getAllByWorkoutId(UUID workoutID) {

		return workoutSessionRepository
				.findAllByWorkoutId(workoutID)
				.stream()
				.map(workoutSessionMapper::toWorkoutSessionOutputDTO)
				.collect(Collectors.toList());
	}

	@Override
	public WorkoutSessionOutputDTO updateWorkoutSession(UUID id, WorkoutSessionInputDTO workoutSessionDetails) {

		logger.info("Updating workout session with ID: {}", id);

		var existingWorkoutSession = requireWorkoutSession(id);

		var updatedWorkoutSession = workoutSessionMapper.toWorkoutSession(workoutSessionDetails);
		updatedWorkoutSession.setId(existingWorkoutSession.getId()); // Ensure the ID is preserved

		return workoutSessionMapper.toWorkoutSessionOutputDTO(workoutSessionRepository.save(updatedWorkoutSession));
	}

	@Override
	public void updateWorkoutSessions(List<WorkoutSession> workoutSessionList) {

		for (WorkoutSession workoutSession : workoutSessionList)
			updateWorkoutSession(workoutSession.getId(), workoutSessionMapper.toWorkoutSessionInputDTO(workoutSession));
	}

	@Override
	@Transactional
	public void deleteWorkoutSession(UUID id) {

		logger.info("Deleting workout session with ID: {}", id);

		var workoutSession = workoutSessionMapper.toWorkoutSession(getWorkoutSessionById(id));

		logger.info("Workout session {}", workoutSession);

		workoutService.decrementNumberOfExercises(workoutSession.getWorkout().getId());

		workoutSessionRepository.deleteByCustomQuery(id);

	}

	@Override
	public void deleteAllWorkoutSessions() {

		logger.info("Deleting all workout sessions.");

		workoutSessionRepository.deleteAll();
	}

	@Override
	public Optional<WorkoutSessionOutputDTO> getExerciseById(UUID workoutSessionId) {

		return workoutSessionRepository
				.findById(workoutSessionId)
				.map(workoutSessionMapper::toWorkoutSessionOutputDTO);
	}

}
