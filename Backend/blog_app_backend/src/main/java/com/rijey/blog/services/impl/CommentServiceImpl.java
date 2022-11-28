package com.rijey.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijey.blog.entities.Comment;
import com.rijey.blog.entities.Post;
import com.rijey.blog.exceptions.ResourceNotFoundException;
import com.rijey.blog.payloads.CommentDto;
import com.rijey.blog.repositories.CommentRepo;
import com.rijey.blog.repositories.PostRepo;
import com.rijey.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modeMapper;
	

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post"," Id ",postId));
		
		Comment comment = this.modeMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment saved = this.commentRepo.save(comment);
		
		return this.modeMapper.map(saved, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment"," Id ",commentId));
		
		this.commentRepo.delete(comment);

	}

}
