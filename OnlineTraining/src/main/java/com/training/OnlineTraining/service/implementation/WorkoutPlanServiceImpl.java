package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.WorkoutPlanDTO;
import com.training.OnlineTraining.exceptions.WorkoutPlanNotFoundException;
import com.training.OnlineTraining.mapper.WorkoutPlanMapper;
import com.training.OnlineTraining.model.WorkoutPlan;
import com.training.OnlineTraining.repository.WorkoutPlanRepository;
import com.training.OnlineTraining.service.WorkoutPlanService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

	private static final Logger logger = LoggerFactory.getLogger(WorkoutPlanServiceImpl.class);

	private final WorkoutPlanRepository workoutPlanRepository;
	private final WorkoutPlanMapper workoutPlanMapper;

	@Override
	public WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO) {

		logger.info("Creating workout plan: {}", workoutPlanDTO);
		var savedWorkoutPlan = workoutPlanRepository.save(workoutPlanMapper.toWorkoutPlan(workoutPlanDTO));
		logger.info("Workout plan created: {}", savedWorkoutPlan);
		return workoutPlanMapper.INSTANCE.toWorkoutPlanDTO(savedWorkoutPlan);
	}

	@Override
	public WorkoutPlanDTO getWorkoutPlanById(UUID workoutPlanID) {

		logger.info("Retrieving workout plan by ID: {}", workoutPlanID);
		WorkoutPlan workoutPlan = requireWorkoutPlan(workoutPlanID);
		logger.info("Retrieved workout plan: {}", workoutPlan);
		return workoutPlanMapper.toWorkoutPlanDTO(workoutPlan);
	}

	private WorkoutPlan requireWorkoutPlan(UUID workoutPlanID) {

		return workoutPlanRepository.findById(workoutPlanID)
				.orElseThrow(() -> {
					logger.error("Workout plan with ID {} not found", workoutPlanID);
					return new WorkoutPlanNotFoundException(workoutPlanID);
				});
	}

	@Override
	public List<WorkoutPlanDTO> getAllWorkoutPlans() {

		logger.info("Retrieving all workout plans");
		List<WorkoutPlanDTO> workoutPlanDTOs = workoutPlanRepository.findAll().stream()
				.map(workoutPlanMapper::toWorkoutPlanDTO)
				.collect(Collectors.toList());
		logger.info("Retrieved {} workout plans", workoutPlanDTOs.size());
		return workoutPlanDTOs;
	}

	@Override
	public WorkoutPlanDTO updateWorkoutPlan(UUID workoutPlanID, WorkoutPlanDTO workoutPlanDTO) {

		logger.info("Updating workout plan with ID {}: {}", workoutPlanID, workoutPlanDTO);
		if (workoutPlanRepository.doesNotExistById(workoutPlanID)) {
			logger.error("Workout plan with ID {} not found", workoutPlanID);
			throw new WorkoutPlanNotFoundException(workoutPlanID);
		}

		var updatedWorkoutPlan = workoutPlanMapper.toWorkoutPlan(workoutPlanDTO);
		updatedWorkoutPlan.setId(workoutPlanID);

		WorkoutPlan savedWorkoutPlan = workoutPlanRepository.save(updatedWorkoutPlan);
		logger.info("Workout plan updated: {}", savedWorkoutPlan);
		return workoutPlanMapper.toWorkoutPlanDTO(savedWorkoutPlan);
	}

	@Override
	public void deleteWorkoutPlan(UUID workoutPlanID) {

		logger.info("Deleting workout plan with ID: {}", workoutPlanID);
		if (workoutPlanRepository.doesNotExistById(workoutPlanID)) {
			logger.error("Workout plan with ID {} not found", workoutPlanID);
			throw new WorkoutPlanNotFoundException(workoutPlanID);
		}

		workoutPlanRepository.deleteById(workoutPlanID);
		logger.info("Workout plan deleted successfully");
	}

}
