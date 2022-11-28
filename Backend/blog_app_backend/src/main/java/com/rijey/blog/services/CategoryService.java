package com.rijey.blog.services;

import java.util.List;

import com.rijey.blog.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
	
	void deleteCategory(int categoryId);
	
	CategoryDto getCategory(int categoryId);
	
	List<CategoryDto> getAllCategory();
	
}
