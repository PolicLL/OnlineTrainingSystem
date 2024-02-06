package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {

	private final MeasurementService measurementService;

	@GetMapping("/{contractId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String showMeasurementForm(@PathVariable UUID contractId, Model model) {

		model.addAttribute("contractId", contractId);
		model.addAttribute("measurementDto", new MeasurementDTO());
		return "client/measurement_form";
	}

	@PostMapping("/{contractId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String createMeasurement(@PathVariable UUID contractId,
	                                @Valid MeasurementDTO measurementDto,
	                                BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "client/measurement_form";
		}

		measurementService.createMeasurement(measurementDto);

		return "index";
	}

	@GetMapping("/personal/{contractId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String getPersonalMeasurements(@PathVariable UUID contractId, Model model,
	                                      @RequestParam(defaultValue = "1") int page,

	                                      @RequestParam(defaultValue = "10") int size) {

		PageRequest pageRequest = PageRequest.of(page - 1, size);

		Page<Measurement> measurementPage = measurementService.getMeasurementsByContractIdSortedByDatePageable(contractId, pageRequest);

		model.addAttribute("measurements", measurementPage);
		model.addAttribute("currentPage", measurementPage.getNumber() + 1);
		model.addAttribute("totalPages", measurementPage.getTotalPages());

		return "client/personal_measurements";
	}


	@GetMapping("/personal/{contractId}/update/{measurementId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String showUpdateForm(@PathVariable UUID contractId, @PathVariable UUID measurementId, Model model) {

		var measurementDto = measurementService.getMeasurementById(measurementId);
		model.addAttribute("contractId", contractId);
		model.addAttribute("measurementId", measurementId);
		model.addAttribute("measurementDto", measurementDto);
		return "client/update_measurement_form";
	}

	@PutMapping("/personal/{contractId}/update/{measurementId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String updateMeasurement(@PathVariable UUID contractId,
	                                @PathVariable UUID measurementId,
	                                @Valid MeasurementDTO measurementDto,
	                                BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "client/update_measurement_form";
		}

		measurementService.updateMeasurement(measurementId, measurementDto);

		return "redirect:/measurements/personal/{contractId}";
	}

	@DeleteMapping("/personal/{contractId}/delete/{measurementId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String deleteMeasurement(@PathVariable UUID contractId,
	                                @PathVariable UUID measurementId) {

		measurementService.deleteMeasurement(measurementId);
		return "redirect:/measurements/personal/{contractId}";
	}


}
