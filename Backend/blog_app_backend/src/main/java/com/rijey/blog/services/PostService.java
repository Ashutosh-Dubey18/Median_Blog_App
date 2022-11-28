package com.rijey.blog.services;

import java.util.List;

import com.rijey.blog.entities.Post;
import com.rijey.blog.payloads.PostDto;
import com.rijey.blog.payloads.PostResponse;

public interface PostService {
	
	
	PostDto createPost(PostDto postDto,int userId,int categoryId);
	
	PostDto updatePost(PostDto postDto,int postId);
	
	void deletePost(int postId);
	
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	PostDto getPostById(int postId);
	
	List<PostDto> getPostByCategory(int categoryId);
	
	List<PostDto> getPostByUser(int userId);
	
	List<PostDto> searchPosts(String title);

}
