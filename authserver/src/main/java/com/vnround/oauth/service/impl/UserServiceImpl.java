package com.vnround.oauth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vnround.oauth.model.User;
import com.vnround.oauth.repository.UserRepository;
import com.vnround.oauth.service.UserService;


@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(userId);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}
	
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}
