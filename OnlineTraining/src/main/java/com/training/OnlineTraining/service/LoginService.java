package com.training.OnlineTraining.service;

import com.training.OnlineTraining.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;


public interface LoginService {

	String processLogin(String email, HttpSession session, Model model, Role role);

}
