package com.rijey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rijey.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
}
