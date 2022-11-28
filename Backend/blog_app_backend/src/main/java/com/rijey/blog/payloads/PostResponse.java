package com.rijey.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	
	private List<PostDto> content;
	
	private long pageNumber;
	
	private long pageSize;
	
	private long totalElements;
	
	private long totalPages;
	
	private boolean lastPage;
	
	
}
