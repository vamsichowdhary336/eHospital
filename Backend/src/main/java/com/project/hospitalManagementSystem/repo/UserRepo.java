package com.project.hospitalManagementSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hospitalManagementSystem.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByUsername(String username);

	List<User> findByStaffMemberTrue();
}
