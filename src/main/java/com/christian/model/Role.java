package com.christian.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.christian.model.User;


@Entity
@Table(name="role") // Table name
public class Role {
    @Column(name="roleId")
	@Id	
    private int userId;
    @Column(name="roleType")
    private roleName roleType;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

enum roleName{
	ADMIN,
	DB_OPERATOR,
	CUSTOMER,
	GUEST
}