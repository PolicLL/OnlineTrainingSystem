package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.input.ExerciseInputDTO;
import com.training.OnlineTraining.dto.output.ExerciseOutputDTO;
import com.training.OnlineTraining.model.enums.ExerciseDifficultyLevel;
import com.training.OnlineTraining.model.enums.ExerciseEquipment;
import com.training.OnlineTraining.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/exercise")
@AllArgsConstructor
public class ExerciseController {

	private final ExerciseService exerciseService;
	private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

	@GetMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String showAddExerciseForm(Model model) {

		logger.info("Displaying add exercise form.");

		List<ExerciseDifficultyLevel> difficultyLevels = Arrays.asList(ExerciseDifficultyLevel.values());
		List<ExerciseEquipment> exerciseEquipmentList = Arrays.asList(ExerciseEquipment.values());

		model.addAttribute("difficultyLevels", difficultyLevels);
		model.addAttribute("exerciseEquipmentList", exerciseEquipmentList);
		model.addAttribute("exercise", new ExerciseInputDTO());

		return "exercise/exerciseCreateForm";
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String createExercise(@ModelAttribute ExerciseInputDTO exerciseInputDTO, Model model) {

		logger.info("Creating a new exercise.");

		var createdExercise = exerciseService.createExercise(exerciseInputDTO);

		return "redirect:/exercise";
	}

	@GetMapping("/details/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String showExerciseDetails(@PathVariable UUID id, Model model) {

		logger.info("Displaying exercise details for ID: {}", id);

		var exercise = exerciseService.getExerciseById(id);

		model.addAttribute("exercise", exercise);
		return "exercise/exerciseDetails";
	}

	@GetMapping()
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH')")
	public String getAllExercises(Model model,
	                              @RequestParam(defaultValue = "1") int page,
	                              @RequestParam(defaultValue = "10") int size) {

		PageRequest pageRequest = PageRequest.of(page - 1, size);

		Page<ExerciseOutputDTO> exercisePage = exerciseService.getAllExercisesPageable(pageRequest);

		model.addAttribute("exercises", exercisePage);
		model.addAttribute("currentPage", exercisePage.getNumber() + 1);
		model.addAttribute("totalPages", exercisePage.getTotalPages());

		return "exercise/exerciseList";
	}


	@GetMapping("/update/{id}")
	public String getUpdateFormForExercise(@PathVariable UUID id, Model model) {

		logger.info("Displaying update form for exercise ID: {}", id);

		var tempExercise = exerciseService.getExerciseById(id);
		List<ExerciseDifficultyLevel> difficultyLevels = Arrays.asList(ExerciseDifficultyLevel.values());
		List<ExerciseEquipment> exerciseEquipmentList = Arrays.asList(ExerciseEquipment.values());

		model.addAttribute("exerciseEquipmentList", exerciseEquipmentList);
		model.addAttribute("difficultyLevels", difficultyLevels);
		model.addAttribute("exerciseForUpdating", tempExercise);

		return "exercise/exerciseUpdateForm";
	}

	@PostMapping("/update/{id}")
	public String updateExercise(@PathVariable String id, @ModelAttribute("exercise") ExerciseInputDTO exerciseInputDTO, Model model) {

		logger.info("Updating exercise for ID: {}", id);
		logger.info("ExerciseDTO: {}", exerciseInputDTO);

		exerciseService.updateExercise(UUID.fromString(id), exerciseInputDTO);

		return "redirect:/exercise";
	}

	@PostMapping("/delete/{id}")
	public String deleteExercise(@PathVariable UUID id) {

		logger.info("Deleting exercise for ID: {}", id);
		exerciseService.deleteExercise(id);
		return "redirect:/exercise";
	}

	@GetMapping("/client-details/{id}")
	public String showClientExerciseDetails(@PathVariable UUID id, Model model) {

		var exercise = exerciseService.getExerciseById(id);

		model.addAttribute("exercise", exercise);

		return "client/client_exercise_details";
	}

	@GetMapping("/progress")
	public String showError() {

		return "client/no_exercise_found";
	}

}
