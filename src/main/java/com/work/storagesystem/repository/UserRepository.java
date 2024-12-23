package com.work.storagesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.storagesystem.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	boolean existsByUsername(String username);

}
