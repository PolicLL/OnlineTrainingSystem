package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.ContractDTO;
import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.model.enums.Role;
import com.training.OnlineTraining.service.AdminService;
import com.training.OnlineTraining.service.WorkoutService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/admins")
public class AdminController {

	private final AdminService adminService;
	private final WorkoutService workoutService;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getAdminPage() {

		return "admin/admin_page";
	}


	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getAllUsers(@RequestParam(name = "roleFilter", required = false) String roleFilter, Model model) {

		List<UserDTO> users = adminService.getUsers(roleFilter);

		model.addAttribute("users", users);
		model.addAttribute("availableRoles", Role.values());
		return "admin/users";
	}

	@DeleteMapping("/users/delete/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable UUID userId) {

		adminService.deleteUser(userId);
		return "/admin/admin_page";
	}

	@GetMapping("/users/update/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getUpdateUserPage(@PathVariable UUID userId, Model model) {

		model.addAttribute("user", adminService.getUserById(userId));
		return "admin/update_user";
	}

	@PostMapping("/users/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateUser(@ModelAttribute("user") UserDTO userDto) {

		adminService.updateUser(userDto.getId(), userDto);
		return "redirect:/admins/users";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/contracts")
	public String getAllContracts(Model model, HttpSession session) {

		model.addAttribute("contracts", adminService.getAllContracts());
		session.removeAttribute("contractID");
		return "admin/contracts_page";
	}

	@GetMapping("/contracts/update/{contractId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getUpdateContractPage(@PathVariable UUID contractId, Model model) {

		ContractDTO contract = adminService.getContractById(contractId);
		model.addAttribute("contract", contract);
		model.addAttribute("coachId", contract.getCoach().getId());
		model.addAttribute("clientId", contract.getClient().getId());
		return "admin/update_contract";
	}

	@PostMapping("/contracts/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateContract(@ModelAttribute("contract") ContractDTO contractDto) {

		adminService.updateContract(contractDto.getId(), contractDto);
		return "redirect:/admins/contracts";
	}

	@DeleteMapping("/contracts/delete/{contractId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteContract(@PathVariable UUID contractId) {

		adminService.deleteContract(contractId);
		return "redirect:/admins/contracts";
	}

}
