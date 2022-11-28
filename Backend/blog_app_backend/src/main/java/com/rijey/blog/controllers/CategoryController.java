package com.rijey.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rijey.blog.payloads.CategoryDto;
import com.rijey.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto saved = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(saved,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId} ")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto
			,@PathVariable int categoryId){
		CategoryDto updated = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable int categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new String("Delete Successful"),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable int categoryId){
		
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> list = this.categoryService.getAllCategory();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
