package com.rijey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rijey.blog.exceptions.ApiException;
import com.rijey.blog.payloads.JwtAuthRequest;
import com.rijey.blog.payloads.JwtAuthResponse;
import com.rijey.blog.payloads.UserDto;
import com.rijey.blog.security.JwtTokenHelper;
import com.rijey.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
	){
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetail);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken userPassAuthToken = 
				new UsernamePasswordAuthenticationToken(username,password);
		try {
			this.authManager.authenticate(userPassAuthToken);
		}catch(BadCredentialsException e) {
			throw new ApiException("Invalid details");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<>(registerNewUser,HttpStatus.CREATED);
	}

}




