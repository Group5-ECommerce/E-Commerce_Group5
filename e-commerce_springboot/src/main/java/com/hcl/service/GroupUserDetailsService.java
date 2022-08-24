package com.hcl.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.entity.User;

@Service
public class GroupUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<User> user = userService.getUserByUsername(username);
		return user.map(GroupUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " was not found."));
	}
}
