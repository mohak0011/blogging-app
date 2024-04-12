package com.mohak.bloggingapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohak.bloggingapp.entities.Category;
import com.mohak.bloggingapp.entities.Comment;
import com.mohak.bloggingapp.entities.Post;
import com.mohak.bloggingapp.exceptions.ResourceNotFoundException;
import com.mohak.bloggingapp.payloads.CategoryDto;
import com.mohak.bloggingapp.payloads.CommentDto;
import com.mohak.bloggingapp.repositories.CommentRepo;
import com.mohak.bloggingapp.repositories.PostRepo;
import com.mohak.bloggingapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		Comment comment = this.dtoToComment(commentDto);
		comment.setPost(post);
		Comment savedcomment= this.commentRepo.save(comment);
		
		return this.commentToDto(savedcomment);
	}

	@Override
	public void deleteComment(Integer commentId) {
	
		Comment com =this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));;
				this.commentRepo.delete(com);

	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 // Converts a CommentDto object to a Comment object using ModelMapper
    public Comment dtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    // Converts a Comment object to a CommentDt object using ModelMapper
    public CommentDto commentToDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }

}
