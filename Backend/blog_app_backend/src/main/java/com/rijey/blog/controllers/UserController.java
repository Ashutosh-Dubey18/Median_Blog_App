package com.rijey.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rijey.blog.payloads.UserDto;
import com.rijey.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
		
		UserDto user = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(user,HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable int userId){
		
		UserDto updatedUser = this.userService.updateUser(userDto,userId);
		
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable int userId){
		
		UserDto userDto = this.userService.getUserById(userId);
		
		return new ResponseEntity<>(userDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		return new ResponseEntity<>(this.userService.getAllUsers(),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable int userId){
		
		this.userService.deleteUser(userId);
		
		return new ResponseEntity<>(new String("Delete Successful"),HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<List<UserDto>> deleteUser(){
		
		List<UserDto> ls = this.userService.getAllUsers();
		List<UserDto> deletedUsers = new ArrayList<>();
		for(UserDto us: ls) {
			deletedUsers.add(us);
			this.userService.deleteUser(us.getId());
		}
		
		return new ResponseEntity<>(deletedUsers,HttpStatus.OK);
	}
	
	
}
