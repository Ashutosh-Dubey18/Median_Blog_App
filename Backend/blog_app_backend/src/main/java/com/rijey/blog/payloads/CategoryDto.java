package com.rijey.blog.payloads;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	
	private int categoryId;
	
	@NotBlank(message = "Category title cannot be empty")
	private String categoryTitle;
	
	@NotBlank(message = "Category description cannot be empty")
	private String categoryDescription;
	
}
