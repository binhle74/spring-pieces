package com.vnround.oauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcUserDetailsManagerConfigurer = 
				auth.jdbcAuthentication().passwordEncoder(passwordEncoder()).dataSource(dataSource);
		JdbcUserDetailsManager jdbcUserDetailsManager = jdbcUserDetailsManagerConfigurer.getUserDetailsService();
		jdbcUserDetailsManager.setEnableAuthorities(false);
		jdbcUserDetailsManager.setEnableGroups(true);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/login**", "/oauth/authorize**", "/oauth/token**", "/oauth/check_token**", "/error**").permitAll()
			.and().authorizeRequests().anyRequest().authenticated()
			.and().formLogin().permitAll().and().httpBasic();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		UserDetailsService userDetailsService = super.userDetailsServiceBean();
		return userDetailsService;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
