package com.training.OnlineTraining.controller;

import com.training.OnlineTraining.dto.UserDTO;
import com.training.OnlineTraining.exceptions.PasswordMismatchException;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.service.MailService;
import com.training.OnlineTraining.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;


@Controller
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final MailService mailService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/register")
	public String getRegisterPage(Model model) {

		model.addAttribute("registerRequest", new UserDTO());
		logger.info("Displaying register page.");

		return "registration/register_page";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute UserDTO request, @RequestParam String confirmPassword, Model model) {

		try {
			if (arePasswordsDifferent(request, confirmPassword)) {
				throw new PasswordMismatchException();
			}

			User registeredUser = userService.registerUser(request);

			model.addAttribute("userId", registeredUser.getId());

			return "/auth/become_client_or_coach_page";

		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("registerRequest", request);
			return "registration/register_page";
		}
	}

	private boolean arePasswordsDifferent(UserDTO request, String confirmPassword){
		return !request.getPassword().equals(confirmPassword);
	}

	@GetMapping("/additions")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String getBecomeClientOrCoachPage(Model model, HttpSession session) {

		UUID userId = (UUID) session.getAttribute("userId");
		model.addAttribute("userId", userId);

		return "auth/become_client_or_coach_page";
	}


	@GetMapping("/login")
	public String getLoginPage(Model model) {

		model.addAttribute("loginRequest", new UserDTO());
		return "auth/login_page";
	}


	@GetMapping("/logout")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'COACH', 'CLIENT', 'USER')")
	public String logout(HttpSession session) {

		session.invalidate();
		return "index";
	}

}

