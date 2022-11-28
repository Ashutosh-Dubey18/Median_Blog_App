package com.rijey.blog.services;

import com.rijey.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, int postId);
	
	void deleteComment(int commentId);
}
