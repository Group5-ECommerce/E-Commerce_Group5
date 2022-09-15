//package com.hcl.repo;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.hcl.entity.Order;
//import com.hcl.entity.User;
//
// 
//public interface UserRepository extends JpaRepository<User, Integer>{
//	Optional<User> findByUsername(String username);
//	
//	Optional<User> findByEmail(String email);
//
//	Optional<User> findByOktaId(String oktaId);
//}
//
//
