package com.hcl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.entity.RoleName;
import com.hcl.entity.User;


@NoArgsConstructor
@Entity
@Table(name="role") // Table name
public class Role {
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="roleId")
    private int roleId;
    @Column(name="roleType", length = 20)
    private String roleType;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}