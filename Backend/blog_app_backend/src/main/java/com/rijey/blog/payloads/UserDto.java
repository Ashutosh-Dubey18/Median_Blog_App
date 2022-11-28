package com.rijey.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotBlank(message="Name cannot be empty")
	private String name;
	
	@Valid
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message="Invalid Email Address")
	private String email;
	
	@NotBlank(message="Password cannot be empty")
	private String password;
	
	@NotBlank(message="About cannot be empty")
	private String about;
	
	
	private Set<RoleDto> roles = new HashSet<>();


}
