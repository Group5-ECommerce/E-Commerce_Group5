package com.hcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.entity.Role;
import com.hcl.service.RoleService;

@RestController
public class RoleController 
{
	@Autowired
	private RoleService service;
	
	@PostMapping("/addRole")
	public String addRole(@RequestBody Role r)
	{
		service.addRole(r);
		return "Role added";
	}
	
}
