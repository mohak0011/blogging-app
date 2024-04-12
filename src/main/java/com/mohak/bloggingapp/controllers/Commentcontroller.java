package com.mohak.bloggingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohak.bloggingapp.payloads.ApiResponse;
import com.mohak.bloggingapp.payloads.CommentDto;
import com.mohak.bloggingapp.services.CommentService;

@RestController
@RequestMapping("/api/")
public class Commentcontroller {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId)
	{
		CommentDto createComment= this.commentService.createComment(comment, postId);	
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment( @PathVariable Integer commentId)
	{
		 this.commentService.deleteComment(commentId);	
		return new ResponseEntity<ApiResponse>(new ApiResponse("CommentDeleted Successfully!!",true),HttpStatus.OK);
	}

}
