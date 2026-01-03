package com.project.hospitalManagementSystem.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.Report;
import com.project.hospitalManagementSystem.repo.AppointmentRepo;
import com.project.hospitalManagementSystem.repo.ReportRepo;

@Service
public class PatientAppointmentService {

	@Autowired
	private AppointmentRepo appointmentRepo;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	ReportRepo repRepo;

	// Fetch all appointments for the logged-in patient (to be replaced with actual
	// patient filtering logic)
	public List<Appointment> getPatientAppointments(String username) {
		// TODO: Replace with actual patient filtering based on logged-in user
		return appointmentRepo.findByUsername(username);
	}

	// Cancel an appointment
	public void cancelAppointment(Long id) {
		appointmentRepo.deleteById(id);
	}

	// Get appointment details by ID
	public Appointment getAppointmentById(Long id) {
		return appointmentRepo.findById(id).orElse(null);
	}

	// Reschedule the appointment
	public void rescheduleAppointment(Appointment updatedAppointment) {
		Appointment existingAppointment = appointmentRepo.findById(updatedAppointment.getId()).orElse(null);
		if (existingAppointment != null) {
			LocalDate newDate = updatedAppointment.getAppointmentDate();
			LocalTime newTime = updatedAppointment.getAppointmentTime();
			String doctor = existingAppointment.getDoctor();

			// Check if the new slot is available for the same doctor
			if (!appointmentService.isSlotAvailable(doctor, newDate, newTime)) {
				throw new RuntimeException("Slot is already booked, please choose a different time.");
			}

			// Update details
			existingAppointment.setAppointmentDate(newDate);
			existingAppointment.setAppointmentTime(newTime);
			appointmentRepo.save(existingAppointment);
		}
	}

	public List<Report> getMyReports(String username) {

		return repRepo.findByPatientName(username);
	}
}
