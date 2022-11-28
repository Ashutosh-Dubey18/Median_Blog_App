package com.rijey.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import com.rijey.blog.config.AppConstants;
import com.rijey.blog.entities.Role;
import com.rijey.blog.payloads.UserDto;
import com.rijey.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppBackendApplication implements CommandLineRunner {
	
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@Autowired
	private RoleRepo roleRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(BlogAppBackendApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

//		System.out.println(this.passwordEncode.encode("1234"));
		
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> saveAll = this.roleRepo.saveAll(roles);
			
			saveAll.forEach(r -> System.out.println(r.getName()));
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	

}



