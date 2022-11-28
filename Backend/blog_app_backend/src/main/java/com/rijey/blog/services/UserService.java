package com.rijey.blog.services;

import com.rijey.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
	
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,int userId);
	
	UserDto getUserById(int userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(int userId);

}
