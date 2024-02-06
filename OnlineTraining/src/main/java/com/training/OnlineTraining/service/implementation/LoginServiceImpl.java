package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.model.enums.Role;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.ClientService;
import com.training.OnlineTraining.service.CoachService;
import com.training.OnlineTraining.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.UUID;


@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientService clientService;

	@Autowired
	private CoachService coachService;

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public String processLogin(String email, HttpSession session, Model model, Role role) {

		try {
			logger.info("Attempting login for user : {}", email);

			UUID userID = userRepository.findUserIdByEmail(email);

			String firstName = userRepository.findFirstNameByEmail(email).get();


			if (role == Role.CLIENT) {
				var client = clientService.getClientByUserId(userID);
				session.setAttribute("clientId", client.getId());
				session.setAttribute("clientName", firstName);
				logger.info("Login successful. Redirecting to client page for user ID: {}", userID);
				return "redirect:contracts/personal";
			}

			if (role == Role.COACH) {
				var coach = coachService.findByUserId(userID);
				session.setAttribute("userId", userID);
				session.setAttribute("coachId", coach.getId());
				session.setAttribute("coachName", firstName);
				logger.info("Login successful. Redirecting to coach page for user ID: {}", userID);
				return "/coaches/coach-page"; // Return the redirect URL
			}

			if (role == Role.USER) {
				session.setAttribute("userId", userID);
				logger.info("Login successful. Redirecting to user page for user ID: {}", userID);
				return "/additions"; // Return the redirect URL
			}
			//model.addAttribute("userId", authenticated.getId());
			logger.info("User with ID {} is not a client or coach. Redirecting to become_client_or_coach_page.", userID);
			// za pocetak ovo pa cemo admina ukomponirati
			return "/auth/login_page";

		} catch (RuntimeException e) {
			logger.error("Login failed. Error: {}", e.getMessage());
			//model.addAttribute("error", e.getMessage());
			return "/auth/login_page"; // Return the redirect URL for login failure
		}
	}

}
