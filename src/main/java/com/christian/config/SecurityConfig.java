package com.christian.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// Thanks to this page for guidance: https://www.baeldung.com/spring-security-two-login-pages
// Also thanks to Rachid for linking me that page/helping me with it.
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    private DataSource dataSource;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		// auth.jdbcAuthentication()
		// .usersByUsernameQuery("select username, password, id from users where username = ?");
		// I'm going to follow the SpringBootRoleBased demo, and not use this usersByUsernameQuery method.
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER","ADMIN")
		.antMatchers("/").permitAll()
		.and().formLogin();
	}
}
