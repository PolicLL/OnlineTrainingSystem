package com.training.OnlineTraining.controller;


import com.training.OnlineTraining.dto.WorkoutDTO;
import com.training.OnlineTraining.dto.WorkoutSessionDTO;
import com.training.OnlineTraining.service.ExerciseService;
import com.training.OnlineTraining.service.WorkoutSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping("/workoutSession")
public class WorkoutSessionController {

	private final WorkoutSessionService workoutSessionService;
	private final ExerciseService exerciseService;

	private static final Logger logger = LoggerFactory.getLogger(WorkoutSessionController.class);

	public WorkoutSessionController(WorkoutSessionService workoutSessionService, ExerciseService exerciseService) {

		this.workoutSessionService = workoutSessionService;
		this.exerciseService = exerciseService;
	}

	@GetMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String showCreateWorkoutSessionForm(@RequestParam UUID workoutID, Model model) {

		logger.info("Displaying create workout session form.");

		var workoutSessionDTO = new WorkoutSessionDTO(workoutID);

		model.addAttribute("workoutSessionDTO", workoutSessionDTO);
		model.addAttribute("listExercises", exerciseService.getAllExercises());

		return "workout-session/createWorkoutSession";
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String createWorkout(@ModelAttribute("workoutDTO") WorkoutSessionDTO workoutSessionDTO) {

		logger.info("Creating a new workout session. {}", workoutSessionDTO);

		workoutSessionService.createWorkoutSession(workoutSessionDTO);


		return "redirect:/workout/details/" + workoutSessionDTO.getWorkoutId();
	}

	@PostMapping("/update/{workoutID}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String updateWorkoutAndSessions(
			@PathVariable UUID workoutID,
			@ModelAttribute("workout") WorkoutDTO workoutDTO) {

		logger.info("Updating workout and sessions for workout ID: {}", workoutID);

		workoutSessionService.updateWorkoutSessions(workoutDTO.getWorkoutSessions());

		logger.info("Workout and sessions updated successfully.");

		return "redirect:/workout/details/" + workoutID;
	}

	@PostMapping("/delete/{workoutID}/{workoutSessionID}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String deleteWorkoutSession(
			@PathVariable UUID workoutID,
			@PathVariable UUID workoutSessionID
	) {

		logger.info("Deleting workout session with ID: {}", workoutSessionID);

		workoutSessionService.deleteWorkoutSession(workoutSessionID);

		logger.info("Workout session deleted successfully.");

		// Redirect to the appropriate page after deletion
		return "redirect:/workout/details/" + workoutID;
	}

}

