package com.hcl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin").hasAuthority("Admin").antMatchers("/cart")
				.hasAuthority("Customer").antMatchers("/").permitAll().and().oauth2ResourceServer().jwt(); // or
																											// .opaqueToken();

		// process CORS annotations
		http.cors();

		http.csrf().disable();

		// force a non-empty response body for 401's to make the response more browser
		// friendly
		Okta.configureResourceServer401ResponseBody(http);

	}
}
