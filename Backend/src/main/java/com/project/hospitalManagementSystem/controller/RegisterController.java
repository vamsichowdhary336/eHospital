package com.project.hospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.service.RegisterService;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

	@Autowired
	private RegisterService regService;

	// Register new user
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			regService.registerUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
