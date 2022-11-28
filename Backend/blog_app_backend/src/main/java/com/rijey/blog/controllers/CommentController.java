package com.rijey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rijey.blog.payloads.ApiResponse;
import com.rijey.blog.payloads.CommentDto;
import com.rijey.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable int postId
			){
		
		CommentDto saved = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(saved,HttpStatus.CREATED);
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId){
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<>(new ApiResponse("Deleted Successfully",true),HttpStatus.OK);
		
	}
	
}
