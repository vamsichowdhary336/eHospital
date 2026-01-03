package com.project.hospitalManagementSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hospitalManagementSystem.model.Report;

public interface ReportRepo extends JpaRepository<Report, Long> {

	List<Report> findByPatientName(String patientName);

}
