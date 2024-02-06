package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.service.ReportService;
import com.training.OnlineTraining.service.WorkoutService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

	private static final Logger logger = Logger.getLogger(ReportController.class.getName());

	private final WorkoutService workoutService;

	private final ReportService reportService;


	@GetMapping("/show")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public String showPdf(@RequestParam UUID id, Model model) {

		logger.info("Request to show PDF for workout with ID: " + id);

		var workoutOutputDTO = workoutService.getWorkoutById(id);

		model.addAttribute("workout", workoutOutputDTO);

		return "report/workout_pdf_template";
	}

	@GetMapping("/generate")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT')")
	public void generatePdf(@RequestParam UUID id, HttpServletResponse response) throws IOException {

		logger.info("Request to generate PDF for workout with ID: " + id);

		var workout = workoutService.getWorkoutById(id);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=generated-pdf.pdf");

		reportService.generateReportFromHtml(workout, response.getOutputStream());

		logger.info("PDF generation completed for workout with ID: " + id);
	}

}
