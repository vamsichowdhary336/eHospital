package com.project.hospitalManagementSystem.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalManagementSystem.model.Appointment;
import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.repo.AppointmentRepo;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepo appointmentRepo;
	@Autowired
	DoctorAppointmentService doctorService;

	public boolean isSlotAvailable(String doctor, LocalDate date, LocalTime time) {
		Appointment appointment = appointmentRepo.findByDoctorAndAppointmentDateAndAppointmentTime(doctor, date, time);
		return appointment == null;
	}

	public void bookAppointment(Appointment appointment) {
		if (!isSlotAvailable(appointment.getDoctor(), appointment.getAppointmentDate(),
				appointment.getAppointmentTime())) {
			throw new RuntimeException("Slot is already booked, please choose a different time.");
		}
		appointment.setSlotAvailable(true);
		appointmentRepo.save(appointment);
	}

	public List<Appointment> getAppointmentsByDoctorName(String username) {
		return appointmentRepo.findByDoctor(username);
	}

	public List<User> getAllDoctors() {
		return doctorService.getAll();
	}

}
