package com.mohak.bloggingapp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	@Column(name = "Post_Title", length = 50, nullable = false)
	private String title;
	@Column(name = "Content", length = 500)
	private String content;
	private String imageName;
	private Date addedDate;

	// Many-to-one relationship with Category
	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	// Many-to-one relationship with User
	@ManyToOne
	private User user;

	// One-to-many relationship with Comment
	//Foreign key only with post
	@OneToMany(mappedBy="post",cascade=CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
}
