package com.rijey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rijey.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
