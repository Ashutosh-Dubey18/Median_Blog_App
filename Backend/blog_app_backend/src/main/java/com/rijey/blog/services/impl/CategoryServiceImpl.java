package com.rijey.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijey.blog.entities.Category;
import com.rijey.blog.exceptions.ResourceNotFoundException;
import com.rijey.blog.payloads.CategoryDto;
import com.rijey.blog.repositories.CategoryRepo;
import com.rijey.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category saved = this.categoryRepo.save(dtoToCategory(categoryDto));
		return this.categoryToDto(saved);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category"," Id ",categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category saved = this.categoryRepo.save(category);
		return this.categoryToDto(saved);
	}

	@Override
	public void deleteCategory(int categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category"," Id ",categoryId));
		
		this.categoryRepo.delete(category);
		
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category"," Id ",categoryId));
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> list = this.categoryRepo.findAll();
		List<CategoryDto> categories = new ArrayList<>();
		for(Category ct: list)
			categories.add(this.categoryToDto(ct));
		return categories;
	}
	
	
	private Category dtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}
	
	private CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
