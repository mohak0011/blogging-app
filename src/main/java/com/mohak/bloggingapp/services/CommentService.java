package com.mohak.bloggingapp.services;

import com.mohak.bloggingapp.payloads.CategoryDto;
import com.mohak.bloggingapp.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
	CommentDto updateComment(CommentDto commentDto,Integer commentId);
	
}
