package com.mohak.bloggingapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohak.bloggingapp.entities.User;
import com.mohak.bloggingapp.exceptions.ResourceNotFoundException;
import com.mohak.bloggingapp.payloads.UserDto;
import com.mohak.bloggingapp.repositories.UserRepo;
import com.mohak.bloggingapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
	    // Convert the UserDto to a User object
	    User user = this.dtoToUser(userDto);
	    // Save the User object to the database using the UserRepository (userRepo)
	    User savedUser = this.userRepo.save(user);
	    // Convert the saved User object back to a UserDto and return it
	    return this.userToDto(savedUser);
	}
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	    // Find the user by userId in the database
	    User user = this.userRepo.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

	    // Update the user attributes with the values from userDto
	    user.setName(userDto.getName());
	    user.setEmail(userDto.getEmail());
	    user.setPassword(userDto.getPassword());
	    user.setAbout(userDto.getAbout());

	    // Save the updated user to the database
	    User updatedUser = this.userRepo.save(user);

	    // Convert the updated User object to a UserDto and return it
	    UserDto userDto1 = this.userToDto(updatedUser);
	    return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
 
		 User user = this.userRepo.findById(userId)
				 .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
				 
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
	
		List<User> users= this.userRepo.findAll();
		List<UserDto>usersDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return usersDtos;
	}

	@Override
	public void Delete(Integer userId) {
	
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);

	}
	
	//Converts a UserDto object to a User object.
	//This is manual implementation 
	/*public User dtoToUser(UserDto userDto)
	{
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		return user;
	}*/
	
	//Converts a User object to a UserDto object.
	//This is manual implementation 
	/*public UserDto userToDto(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());
		return userDto;
	}*/
	
	//Converts a UserDto object to a User object.
	//This conversion is done using library model mapper(uses source to destination)
	public User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	//Converts a User object to a UserDto object.
	//This conversion is done using library model mapper(uses source to destination)
	public UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
}
