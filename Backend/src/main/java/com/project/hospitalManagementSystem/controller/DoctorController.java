package com.project.hospitalManagementSystem.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.Report;
import com.project.hospitalManagementSystem.repo.ReportRepo;
import com.project.hospitalManagementSystem.service.DoctorService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService docService;

	@Autowired
	private ReportRepo repRepo;

	// Get all appointments of logged-in doctor
	@GetMapping("/appointments")
	public ResponseEntity<List<Appointment>> getAllAppointments(HttpSession session) {
		String username = (String) session.getAttribute("docusername");
		return ResponseEntity.ok(docService.getAllAppointments(username));
	}

	// Deny appointment
	@PostMapping("/deny/{id}")
	public ResponseEntity<String> denyAppointment(@PathVariable long id, HttpSession session) {
		docService.denyAppointment(id, session);
		return ResponseEntity.ok("Appointment cancelled successfully");
	}

	// Check patient exists
	@GetMapping("/check-patient")
	public ResponseEntity<String> checkPatient(@RequestParam String username, HttpSession session) {
		docService.checkPatient(username);
		session.setAttribute("patientname", username);
		return ResponseEntity.ok("Patient exists");
	}

	// Upload report
	@PostMapping(value = "/upload-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadReport(@RequestParam String reportName, @RequestParam MultipartFile file,
			HttpSession session) throws IOException {

		docService.addReport(reportName, file, session);
		return ResponseEntity.ok("Report uploaded successfully");
	}

	// View all reports
	@GetMapping("/reports")
	public ResponseEntity<List<Report>> viewReports() {
		return ResponseEntity.ok(docService.getAllReports());
	}

	// View single report file
	@GetMapping("/report/{id}")
	public ResponseEntity<FileSystemResource> viewReport(@PathVariable Long id) {
		Report report = repRepo.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));

		File file = new File(report.getFilePath());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
				.body(new FileSystemResource(file));
	}

	// Logout
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("Logged out successfully");
	}
}
