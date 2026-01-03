package com.project.hospitalManagementSystem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.Report;
import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.repo.AppointmentRepo;
import com.project.hospitalManagementSystem.repo.ReportRepo;
import com.project.hospitalManagementSystem.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class DoctorService {
	@Autowired
	private AppointmentRepo appointmentRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ReportRepo reportRepo;
	@Autowired
	EmailService emailservice;

	public List<Appointment> getAllAppointments(String username) {
		return appointmentRepo.findByDoctor(username);
	}

	public void denyAppointment(long id, HttpSession session) {

		// 1. Fetch appointment from DB (THIS IS THE KEY)
		Appointment appt = appointmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		// 2. Update status
		appt.setStatus("DENIED");
		appointmentRepo.save(appt);

		// 3. Send email USING DATA FROM DB
		emailservice.sendAppointmentDeniedEmail(appt.getPatientEmail(), appt.getPatientName(), appt.getDoctor()

		);
	}

	public List<User> getAll() {
		return userRepo.findByStaffMemberTrue();
	}

	public void denyAppointment(Long id) {
		Appointment appointment = appointmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
		appointment.setStatus("DENIED");
		appointmentRepo.save(appointment);
	}

	public User checkPatient(String username) {
		User patient = userRepo.findByUsername(username);
		if (patient != null)
			return patient;
		else
			throw new RuntimeException("Patient not Existed !!");
	}

	public void addReport(String reportName, MultipartFile file, HttpSession ses) throws IOException {
		final String baseDir = "C:/MedicalReports/";
		Path patientFolder = Paths.get(baseDir, (String) ses.getAttribute("patientname"));
		Files.createDirectories(patientFolder);

		String storedFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		Path destination = patientFolder.resolve(storedFileName);
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

		Report report = new Report();
		report.setPatientName((String) ses.getAttribute("patientname"));
		report.setReportName(reportName);
		report.setFileName(file.getOriginalFilename());
		report.setFilePath(destination.toString());
		report.setDoctorName((String) ses.getAttribute("docusername"));
		reportRepo.save(report);
	}

	public List<Report> getAllReports() {

		return reportRepo.findAll();
	}
}
