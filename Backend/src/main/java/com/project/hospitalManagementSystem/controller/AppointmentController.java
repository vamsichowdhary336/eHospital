package com.project.hospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.repo.UserRepo;
import com.project.hospitalManagementSystem.service.AppointmentService;
import com.project.hospitalManagementSystem.service.DoctorAppointmentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/appointments")

public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DoctorAppointmentService doctorService;
	@Autowired
	private UserRepo userRepo;

	// Get doctors list (used by frontend to show form)
	@GetMapping("/doctors")
	public ResponseEntity<?> getDoctors() {
		return ResponseEntity.ok(doctorService.getAll());
	}

	@PostMapping("/booking")
	public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment, HttpSession session) {

		String username = (String) session.getAttribute("username");
		if (username == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
		}

		User user = userRepo.findByUsername(username); // fetch logged user

		appointment.setUsername(username);
		appointment.setPatientName(user.getFirstname());
		appointment.setPatientEmail(user.getEmail());
		appointment.setStatus("Scheduled");

		appointmentService.bookAppointment(appointment);
		return ResponseEntity.status(HttpStatus.CREATED).body("Appointment booked successfully");
	}

}
