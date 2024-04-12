package com.mohak.bloggingapp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mohak.bloggingapp.entities.Category;
import com.mohak.bloggingapp.entities.Comment;
import com.mohak.bloggingapp.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	@NotBlank
	@Size(min = 3, message = "Posts Title minimum should have 3 chars!")
	private String title;
	@NotBlank
	@Size(min = 10, message = "Posts Content minimum should have 10 chars!")
	private String content;
	private String imageName;
	private Date addedDate;

	private CategoryDto category;
	private UserDto user;

	private Set<CommentDto> comments = new HashSet<>();
}
