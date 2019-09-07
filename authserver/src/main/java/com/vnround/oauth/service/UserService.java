package com.vnround.oauth.service;

import java.util.List;

import com.vnround.oauth.model.User;

public interface UserService {
	List<User> findAll();
	
	void delete(Long userId);
	
	User save(User user);
}
