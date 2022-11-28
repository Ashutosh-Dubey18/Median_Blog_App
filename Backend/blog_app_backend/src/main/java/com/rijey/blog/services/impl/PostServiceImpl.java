package com.rijey.blog.services.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rijey.blog.entities.Category;
import com.rijey.blog.entities.Post;
import com.rijey.blog.entities.User;
import com.rijey.blog.exceptions.ResourceNotFoundException;
import com.rijey.blog.payloads.PostDto;
import com.rijey.blog.payloads.PostResponse;
import com.rijey.blog.repositories.CategoryRepo;
import com.rijey.blog.repositories.PostRepo;
import com.rijey.blog.repositories.UserRepo;
import com.rijey.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User"," Id ",userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category"," Id ",categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post saved = this.postRepo.save(post);
		
		return this.modelMapper.map(saved, PostDto.class);
	}


	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() 
				-> new ResourceNotFoundException("Post"," Id ",postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post saved = this.postRepo.save(post);
		
		return this.modelMapper.map(saved, PostDto.class);
	}


	@Override
	public void deletePost(int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() 
				-> new ResourceNotFoundException("Post"," Id ",postId));
		
		this.postRepo.delete(post);
		
	}


	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize, String sortBy,String sortDir) {
		
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pg = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pg);
		
		List<Post> ps= pagePost.getContent();
		 
		 List<PostDto> saved = ps.stream().map((post) 
				 -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		 PostResponse postResponse = new PostResponse();
		 
		 postResponse.setContent(saved);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setTotalPages(pagePost.getTotalPages());
		 postResponse.setLastPage(pagePost.isLast());
		 
		return postResponse;
	}


	@Override
	public PostDto getPostById(int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() 
				-> new ResourceNotFoundException("Post"," Id ",postId));
		
		
		return this.modelMapper.map(post, PostDto.class);
	}


	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category"," Id ",categoryId));
		
		List<Post> post = this.postRepo.findByCategory(cat);
		
		List<PostDto> collect = post.stream().map((p) -> 
			this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		
		return collect;
	}


	@Override
	public List<PostDto> getPostByUser(int userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User"," Id ",userId));
		
		List<Post> post = this.postRepo.findByUser(user);
		
		
		List<PostDto> collect = post.stream().map((p) -> 
		this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		
		return collect;
	}


	@Override
	public List<PostDto> searchPosts(String title) {
		
		List<Post> post = this.postRepo.findByTitleContaining(title);
		
		
		List<PostDto> saved = post.stream().map((p) -> 
		this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		return saved;
	}

}



