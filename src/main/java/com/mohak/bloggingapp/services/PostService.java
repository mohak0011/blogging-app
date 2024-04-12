package com.mohak.bloggingapp.services;

import java.util.List;

import com.mohak.bloggingapp.entities.Post;
import com.mohak.bloggingapp.entities.User;
import com.mohak.bloggingapp.payloads.PostDto;
import com.mohak.bloggingapp.payloads.UserDto;
import com.mohak.bloggingapp.utilities.PostResponseofPaging;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	void deletPost(Integer postId);
//	List<PostDto> getAllPosts();
	PostResponseofPaging getAllPosts(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDto>getPostByUser(Integer userId);
	
	//search Posts
	List<PostDto>searchPosts(String keyword);
	

}
