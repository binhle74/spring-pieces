package com.vnround.oauth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnround.oauth.model.User;
import com.vnround.oauth.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public List<User> all() {
		return this.userService.findAll();
	}
	
	@PostMapping
	public User add(@RequestBody User user) {
		return this.userService.save(user);
	}
	
	@DeleteMapping("/del/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.userService.delete(id);
		return "success";
	}
	
	@GetMapping("/user/me")
	public Principal user(Principal principal) {
		return principal;
	}
}
