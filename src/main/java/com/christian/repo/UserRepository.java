package com.christian.repo;

import org.springframework.data.jpa.repository.JpaRepository;



import com.christian.model.User;

 
public interface UserRepository extends JpaRepository<User, Integer>{

}


