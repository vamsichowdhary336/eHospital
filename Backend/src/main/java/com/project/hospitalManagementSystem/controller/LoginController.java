package com.project.hospitalManagementSystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.service.LoginService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> req, HttpSession session) {

		String username = req.get("username");
		String password = req.get("password");

		User user = loginService.checkUser(username);

		// User not found
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
		}

		// Invalid login
		if (!loginService.validateLogin(user, password)) {

			if (user.isAccountLocked()) {
				return ResponseEntity.status(HttpStatus.LOCKED)
						.body(Map.of("message", "Account locked. Try again after 1 minute"));
			}

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("message", "Incorrect username or password"));
		}

		// Successful login
		session.setAttribute("username", user.getUsername());
		session.setAttribute("email", user.getEmail());
		session.setMaxInactiveInterval(30 * 60);

		if (user.isStaffMember()) {
			session.setAttribute("docusername", user.getUsername());
		}

		return ResponseEntity.ok(Map.of("role", user.isStaffMember() ? "DOCTOR" : "PATIENT", "name",
				user.getFirstname() + " " + user.getLastname(), "message", "User logged in successfully"));
	}
}
