package com.project.hospitalManagementSystem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.Report;
import com.project.hospitalManagementSystem.repo.ReportRepo;
import com.project.hospitalManagementSystem.service.PatientAppointmentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/patient")
public class PatientAppointmentController {

	@Autowired
	private PatientAppointmentService patientAppointmentService;

	@Autowired
	private ReportRepo repRepo;

	// View all appointments
	@GetMapping("/appointments")
	public ResponseEntity<?> viewAppointments(HttpSession session) {
		String username = (String) session.getAttribute("username");
		return ResponseEntity.ok(patientAppointmentService.getPatientAppointments(username));
	}

	// Cancel appointment
	@PostMapping("/appointments/cancel/{id}")
	public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
		patientAppointmentService.cancelAppointment(id);
		return ResponseEntity.ok("Appointment cancelled successfully");
	}

	// Get appointment details (for reschedule)
	@GetMapping("/appointments/{id}")
	public ResponseEntity<?> getAppointment(@PathVariable Long id) {
		return ResponseEntity.ok(patientAppointmentService.getAppointmentById(id));
	}

	// Reschedule appointment
	@PostMapping("/appointments/reschedule")
	public ResponseEntity<?> reschedule(@RequestBody Appointment appointment) {
		patientAppointmentService.rescheduleAppointment(appointment);
		return ResponseEntity.ok("Appointment rescheduled successfully");
	}

	// View my reports
	@GetMapping("/reports")
	public ResponseEntity<?> getReports(HttpSession session) {
		String username = (String) session.getAttribute("username");
		return ResponseEntity.ok(patientAppointmentService.getMyReports(username));
	}

	// Download report
	@GetMapping("/reports/download/{id}")
	public ResponseEntity<?> downloadReport(@PathVariable Long id) throws IOException {
		Report report = repRepo.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));

		File file = new File(report.getFilePath());
		if (!file.exists()) {
			return ResponseEntity.status(404).body("File not found");
		}

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getFileName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
}
