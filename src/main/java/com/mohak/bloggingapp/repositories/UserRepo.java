package com.mohak.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohak.bloggingapp.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

}
