package com.project.hospitalManagementSystem.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hospitalManagementSystem.model.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

	List<Appointment> findByDoctor(String doctor);

	List<Appointment> findByDoctorAndAppointmentDate(String doctor, LocalDate appointmentDate);

	Appointment findByDoctorAndAppointmentDateAndAppointmentTime(String doctor, LocalDate appointmentDate,
			LocalTime appointmentTime);

	List<Appointment> findByUsername(String username);
}
