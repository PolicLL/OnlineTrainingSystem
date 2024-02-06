package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.ContractDto;
import com.training.OnlineTraining.service.CoachService;
import com.training.OnlineTraining.service.ContractService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/contracts")
public class ContractController {


	private ContractService contractService;
	private CoachService coachService;

	@GetMapping("/{coachId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String viewContract(@PathVariable UUID coachId, Model model, HttpSession session) {

		UUID clientId = (UUID) session.getAttribute("clientId");
		Double monthlyPrice = coachService.getMonthlyPriceById(coachId);

		model.addAttribute("coachId", coachId);
		model.addAttribute("clientId", clientId);
		model.addAttribute("contract", new ContractDto());
		model.addAttribute("monthlyPrice", monthlyPrice);

		return "client/contract_page";
	}

	@PostMapping("/{coachId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String createContract(@PathVariable UUID coachId, @ModelAttribute ContractDto contractDto, Model model) {

		var savedContract = contractService.createContract(contractDto);
		UUID contractId = savedContract.getId();

		return "redirect:/measurements/" + contractId;
	}

	@GetMapping("/personal")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
	public String getPersonalContracts(Model model, HttpSession session) {

		UUID clientId = (UUID) session.getAttribute("clientId");

		String clientName = (String) session.getAttribute("clientName");

		var contracts = contractService.getContractsByClientId(clientId);
		model.addAttribute("contracts", contracts);
		model.addAttribute("clientName", clientName);

		return "client/personal_contracts";
	}

}
