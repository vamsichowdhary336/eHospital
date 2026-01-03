package com.project.hospitalManagementSystem.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.repo.UserRepo;

@Service
public class RegisterService {

	@Autowired
	private UserRepo userRepo;

	private static final String BASE_PATH = "C:/HospitalRecords/records/";

	public User registerUser(User user) {
		if (userRepo.existsByUsername(user.getUsername())) {
			throw new RuntimeException("Username already exists. Please choose a different one.");
		}

		String userFolder = BASE_PATH + user.getUsername();
		File folder = new File(userFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		user.setRecordPath(userFolder);
		return userRepo.save(user);
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
