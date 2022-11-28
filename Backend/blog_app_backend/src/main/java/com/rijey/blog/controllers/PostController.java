package com.rijey.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rijey.blog.config.AppConstants;
import com.rijey.blog.entities.Post;
import com.rijey.blog.payloads.ApiResponse;
import com.rijey.blog.payloads.PostDto;
import com.rijey.blog.payloads.PostResponse;
import com.rijey.blog.services.FileService;
import com.rijey.blog.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto
			,@PathVariable int userId, @PathVariable int catId){
		
		PostDto created = this.postService.createPost(postDto, userId, catId);
		
		return new ResponseEntity<>(created,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
		
		List<PostDto> ls = this.postService.getPostByUser(userId);
		
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId){
		
		List<PostDto> ls = this.postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
		
		PostDto got = this.postService.getPostById(postId);
		
		return new ResponseEntity<>(got,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getPostById(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE ,required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY ,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR ,required=false) String sortDir
			){
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable int postId) {
		this.postService.deletePost(postId);
		
		return new ApiResponse("Deleted Successfully",true);
	}

	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto, @PathVariable int postId) {
		
		PostDto saved = this.postService.updatePost(postdto, postId);
		
		return new ResponseEntity<>(saved,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/search/{keywork}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywork){
		
		List<PostDto> found = this.postService.searchPosts(keywork);
		
		return new ResponseEntity<>(found,HttpStatus.OK);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int postId
			) throws IOException
	{
		PostDto post = this.postService.getPostById(postId);
		String fileName = this.fileservice.uploadImage(path, image);
		
		post.setImageName(fileName);
		this.postService.updatePost(post, postId);
		
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resource = this.fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
	
}









