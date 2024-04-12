package com.mohak.bloggingapp.services;

import java.util.List;

import com.mohak.bloggingapp.entities.User;
import com.mohak.bloggingapp.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void Delete(Integer userId);

}
