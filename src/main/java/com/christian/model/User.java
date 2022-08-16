package com.christian.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id	
    private int userId;
    @Column(name="username", unique=true)
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    
    // Thanks to https://www.baeldung.com/jpa-many-to-many#basic-many-to-many for a guide on ManyToMany and joining tables.
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JsonIgnore
	@JoinTable(
	  name = "user_roles", 
	  joinColumns = @JoinColumn(name = "userId"), 
	  inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<Role>();
}