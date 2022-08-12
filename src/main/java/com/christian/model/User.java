package com.christian.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user") // Table name
public class User {
    @Column(name="userId")
	@Id	
    private int userId;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    
    // Thanks to https://www.baeldung.com/jpa-many-to-many#basic-many-to-many for a guide on ManyToMany and joining tables.
	@ManyToMany
	@JoinTable(
	  name = "user_roles", 
	  joinColumns = @JoinColumn(name = "userId"), 
	  inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles;
}