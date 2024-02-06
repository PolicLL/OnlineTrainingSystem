package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.ClientDto;
import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.CoachFilterParams;
import com.training.OnlineTraining.dto.UpdateClientDTO;
import com.training.OnlineTraining.service.ClientService;
import com.training.OnlineTraining.service.CoachService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

	private final ClientService clientService;
	private final CoachService coachService;

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@GetMapping("/client-page")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String getClientPage(Model model,
	                            @ModelAttribute CoachFilterParams filterParams,
	                            @RequestParam(defaultValue = "1") int page,
	                            @RequestParam(defaultValue = "10") int size) {


		List<CoachDto> filteredCoaches;
		if (filterParams.isNotEmpty()) {
			filteredCoaches = coachService.filterCoaches(filterParams);
			logger.debug("Filtered coaches based on filter parameters.");
		} else {
			Pageable pageable = PageRequest.of(page - 1, size);
			var coachesPage = coachService.coachesWithPagination(filterParams, pageable);
			filteredCoaches = coachesPage.getContent();
			model.addAttribute("currentPage", coachesPage.getNumber() + 1);
			model.addAttribute("totalPages", coachesPage.getTotalPages());
			logger.debug("Fetched coaches with pagination. Page: {}, Size: {}", page, size);
		}

		model.addAttribute("coaches", filteredCoaches);

		return "client/client_page";
	}

	@GetMapping("/register")
	public String getBecomeClientPage(@RequestParam UUID userId, Model model) {

		model.addAttribute("userId", userId);
		model.addAttribute("client", new ClientDto());
		return "client/client_register_page";
	}

	@PostMapping("/register")
	public String becomeClient(@ModelAttribute ClientDto clientDto, @RequestParam UUID userId, Model model) {

		try {
			clientService.registerClient(clientDto, userId);
			return "redirect:/login";
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/settings")
	@PreAuthorize("hasAuthority('CLIENT')")
	public String getSettings(Model model, HttpSession session) {

		UUID clientId = (UUID) session.getAttribute("clientId");
		var client = clientService.getClientsById(clientId);
		model.addAttribute("client", client);
		model.addAttribute("genderOptions", Arrays.asList("Male", "Female"));

		return "client/settings";
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String updateClient(@ModelAttribute UpdateClientDTO updateClientDTO,
	                           HttpSession session, Model model,
	                           BindingResult bindingResult) {

		UUID clientId = (UUID) session.getAttribute("clientId");

		try {
			clientService.updateClient(clientId, updateClientDTO);
			return "index";

		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "client/settings";
		}
	}

}
