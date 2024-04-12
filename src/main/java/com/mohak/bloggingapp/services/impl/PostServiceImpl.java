package com.mohak.bloggingapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mohak.bloggingapp.entities.Category;
import com.mohak.bloggingapp.entities.Post;
import com.mohak.bloggingapp.entities.User;
import com.mohak.bloggingapp.exceptions.ResourceNotFoundException;
import com.mohak.bloggingapp.payloads.PostDto;
import com.mohak.bloggingapp.payloads.UserDto;
import com.mohak.bloggingapp.repositories.CategoryRepo;
import com.mohak.bloggingapp.repositories.PostRepo;
import com.mohak.bloggingapp.repositories.UserRepo;
import com.mohak.bloggingapp.services.PostService;
import com.mohak.bloggingapp.utilities.PostResponseofPaging;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		// Post post = this.modelMapper.map(postDto, Post.class);
		Post post = this.dtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.postToDto(newPost);
		// return this.modelMapper.map(newPost,Post.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		  Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id",postId));
		  post.setTitle(postDto.getTitle());
		  post.setContent(postDto.getContent());
		  post.setImageName(postDto.getImageName());
		  Post updatedPost = this.postRepo.save(post);
		  return this.postToDto(updatedPost);
	}

	@Override
	public void deletPost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);

	}

/*	@Override
	public List<PostDto> getAllPosts(Integer pageNumber,Integer pageSize) {

		// int pageSize = 5;
		// int pageNumber = 2;
		 Pageable pagable = PageRequest.of(pageNumber, pageSize);
		//List<Post> posts = this.postRepo.findAll();
	//	List<Post> posts = this.postRepo.findAll();
		Page<Post> pagepost = this.postRepo.findAll(pagable);
		List<Post>posts=	pagepost.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
		return postDtos;
	} */
	
	public PostResponseofPaging getAllPosts(Integer pageNumber,Integer pageSize,String sortBy, String sortDir) {

		// int pageSize = 5;
		// int pageNumber = 2;
	/*	Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort= Sort.by(sortBy).ascending();
		}
		else
		{
			sort= Sort.by(sortBy).descending();	
		}*/
		Sort sort =(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		 Pageable pagable = PageRequest.of(pageNumber, pageSize,sort);
		//List<Post> posts = this.postRepo.findAll();
	//	List<Post> posts = this.postRepo.findAll();
		Page<Post> pagepost = this.postRepo.findAll(pagable);
		List<Post>posts=	pagepost.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
		PostResponseofPaging  postResponse = new PostResponseofPaging();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastPage(pagepost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Posts", "post id", postId));
		return this.postToDto(post);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToDto(post)).collect(Collectors.toList());
		return postDtos;

	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	 
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
	//	List<PostDto> postsDtos=posts.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
		List<PostDto> postsDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDtos;
	}
	
/*	@Override
	public List<PostDto> searchPosts(String keyword) {
	    // Trim whitespace from the keyword
	    keyword = keyword.trim();
	    
	    // Append wildcard characters to the keyword for a fuzzy search
	    String searchKeyword = "%" + keyword + "%";

	    // Execute the search query with the modified keyword
	    List<Post> posts = this.postRepo.searchByTitleIgnoreCaseContaining(searchKeyword);

	    // Map the Post entities to DTOs
	    List<PostDto> postsDtos = posts.stream()
	                                   .map(post -> this.modelMapper.map(post, PostDto.class))
	                                   .collect(Collectors.toList());

	    return postsDtos;
	}*/

	// Converts a PostDto object to a Post object.
	// This conversion is done using library model mapper(uses source to
	// destination)
	public Post dtoToPost(PostDto postDto) {
		Post post = this.modelMapper.map(postDto, Post.class);
		return post;
	}

	// Converts a Post object to a PostDto object.
	// This conversion is done using library model mapper(uses source to
	// destination)
	public PostDto postToDto(Post post) {
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

}
