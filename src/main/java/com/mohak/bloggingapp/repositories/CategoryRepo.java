package com.mohak.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohak.bloggingapp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
