package com.project.hospitalManagementSystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalManagementSystem.model.User;
import com.project.hospitalManagementSystem.repo.UserRepo;

@Service
public class LoginService {

	@Autowired
	private UserRepo userRepo;

	public User checkUser(String username) {
		return userRepo.findByUsername(username);
	}

	public boolean validateLogin(User user, String password) {
		// Check if account is locked
		if (user.isAccountLocked()) {
			if (user.getLockTime().isBefore(LocalDateTime.now().minusMinutes(1))) {
				// Unlock account after 1 hour
				user.setAccountLocked(false);
				user.setFailedLoginAttempts(0); // Reset failed attempts
				user.setLockTime(null);
				userRepo.save(user);
			} else {
				return false; // Account is still locked
			}
		}

		// Check if password is correct
		if (password.equals(user.getPassword())) {
			// Successful login: reset failed attempts and lock status
			user.setFailedLoginAttempts(0);
			user.setAccountLocked(false);
			user.setLockTime(null);
			userRepo.save(user);
			return true;
		} else {
			// Incorrect password: Increment failed attempts
			int failedAttempts = user.getFailedLoginAttempts() + 1;
			user.setFailedLoginAttempts(failedAttempts);

			if (failedAttempts >= 3) {
				user.setAccountLocked(true);
				user.setLockTime(LocalDateTime.now()); // Lock account at current time
			}

			userRepo.save(user);
			return false;
		}
	}
}
