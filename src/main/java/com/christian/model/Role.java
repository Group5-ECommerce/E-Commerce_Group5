package com.christian.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.christian.model.User;
import com.christian.model.RoleName;

@Data
@Entity
@Table(name="role") // Table name
public class Role {
    @Column(name="roleId")
	@Id	
    private int roleId;
    @Column(name="roleType")
    @Enumerated(EnumType.STRING)
    private RoleName roleType;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}