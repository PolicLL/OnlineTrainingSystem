package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/charts")
public class ChartController {

	private final MeasurementService measurementService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	@GetMapping("/{contractId}")
	public String getPersonalMeasurementsAsc(@PathVariable UUID contractId, Model model) {

		var measurements = measurementService.getMeasurementsByContractIdSortedByDateAsc(contractId);

		model.addAttribute("measurements", measurements);

		return "client/chart";
	}

}
