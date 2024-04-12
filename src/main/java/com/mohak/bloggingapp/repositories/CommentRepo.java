package com.mohak.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohak.bloggingapp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
