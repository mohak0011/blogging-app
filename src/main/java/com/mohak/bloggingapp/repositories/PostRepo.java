package com.mohak.bloggingapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohak.bloggingapp.entities.Category;
import com.mohak.bloggingapp.entities.Post;
import com.mohak.bloggingapp.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
//	@Query("select p from Post p where p.title like : keyword")
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
	List<Post> searchByTitle(@Param("keyword") String title);
	
//	List<Post> searchByTitleIgnoreCaseContaining(String title);
}
