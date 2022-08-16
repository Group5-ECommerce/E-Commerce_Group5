package com.christian.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import com.christian.model.User;

 
public interface RoleRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}


