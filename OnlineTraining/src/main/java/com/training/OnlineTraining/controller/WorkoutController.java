package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.WorkoutTemplate;
import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import com.training.OnlineTraining.service.ExerciseService;
import com.training.OnlineTraining.service.WorkoutService;
import com.training.OnlineTraining.service.WorkoutTemplateCreatorService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/workout")
@AllArgsConstructor
public class WorkoutController {

	private final WorkoutService workoutService;
	private final ExerciseService exerciseService;
	private final WorkoutTemplateCreatorService workoutTemplateCreatorService;
	private static final Logger logger = LoggerFactory.getLogger(WorkoutController.class);

	@GetMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String showCreateWorkoutForm(Model model) {

		logger.info("Displaying create workout form.");

		model.addAttribute("workoutInputDTO", new WorkoutInputDTO());

		return "workout/createWorkout";
	}

	@GetMapping("/create-template")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String showCreateWorkoutUsingTemplateForm(Model model) {

		logger.info("Displaying create workout using template form.");

		model.addAttribute("workoutTemplate", new WorkoutTemplate());

		return "workout/workout-template-form.html";
	}

	@PostMapping("/create-template")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String createWorkoutUsingTemplateForm(@ModelAttribute("workoutTemplate") WorkoutTemplate workoutTemplate, HttpSession session) {

		logger.info("Displaying create workout using template form.");

		UUID contractID = (UUID) session.getAttribute("contractID");
		var workoutInputDTO = workoutTemplateCreatorService.createWorkoutInputDTO(workoutTemplate);
		workoutService.createWorkoutTemplate(workoutInputDTO, contractID);

		return "redirect:/workout";
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String createWorkout(@ModelAttribute("workoutDTO") WorkoutInputDTO workoutInputDTO, HttpSession session) {

		logger.info("Creating a new workout.");

		UUID contractID = (UUID) session.getAttribute("contractID");

		workoutService.createWorkout(workoutInputDTO, contractID);

		return "redirect:/workout";
	}

	@GetMapping()
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public String getAllWorkoutsForContract(@RequestParam(value = "contractID", required = false) String passedContractID, HttpSession session, Model model) {

		logger.info("Fetching all workouts for a contract.");

		UUID contractID = (UUID) session.getAttribute("contractID");

		if (contractID == null) {
			contractID = (passedContractID != null) ? UUID.fromString(passedContractID) : null;
			session.setAttribute("contractID", contractID);
		}

		List<WorkoutOutputDTO> workouts = workoutService.getWorkoutsByContractID(contractID);
		model.addAttribute("workoutsList", workouts);

		UUID clientId = (UUID) session.getAttribute("clientId");

		return clientId == null ? "workout/workoutList" : "workout/workoutClientList";
	}


	@GetMapping("/details/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public String showWorkoutDetails(@PathVariable UUID id, Model model, HttpSession session) {

		logger.info("Displaying workout details for ID: {}", id);

		var workout = workoutService.getWorkoutById(id);

		model.addAttribute("workout", workout);
		model.addAttribute("listExercises", exerciseService.getAllExercises());

		UUID clientId = (UUID) session.getAttribute("clientId");

		return clientId == null ? "workout/workoutDetails" : "workout/workoutClientDetails";
	}

	@GetMapping("/update/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public String showUpdateWorkoutForm(@PathVariable UUID id, Model model, HttpSession session) {

		logger.info("Displaying update workout form for ID: {}", id);

		List<WorkoutStatus> workoutStatuses = Arrays.asList(WorkoutStatus.values());
		model.addAttribute("workoutStatuses", workoutStatuses);

		var workout = workoutService.getWorkoutById(id);
		model.addAttribute("workout", workout);

		UUID clientId = (UUID) session.getAttribute("clientId");


		return clientId == null ? "workout/updateWorkout" : "workout/updateClientWorkout";

	}

	@PostMapping("/update/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public String updateWorkout(@PathVariable UUID id, @ModelAttribute("workout") WorkoutInputDTO workoutInputDTO, HttpSession session) {

		logger.info("Updating workout for ID: {}", id);

		UUID contractID = (UUID) session.getAttribute("contractID");

		workoutService.updateWorkout(id, workoutInputDTO, contractID);


		return "redirect:/workout";
	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String deleteWorkout(@PathVariable UUID id) {

		logger.info("Deleting workout for ID: {}", id);

		workoutService.deleteWorkout(id);

		return "redirect:/workout";
	}

}
