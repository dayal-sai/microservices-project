package com.auth.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.server.dto.AuthRequest;
import com.auth.server.entity.User;
import com.auth.server.exception.UserValidationException;
import com.auth.server.repository.UserRepository;
import com.auth.server.utility.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	JwtUtil util;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		
		return repo.save(user);
		
	}
	
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {
		
			User user = repo.findByUsername(request.getUsername()).orElseThrow(()-> new UserValidationException("Invalid Username"));
		if(!user.getPassword().equals(request.getPassword())) {
            throw new UserValidationException("Invalid Password");
			
		}
		
				
		
		return util.generateToken(user.getUsername(),user.getRole());
	}
	

}
