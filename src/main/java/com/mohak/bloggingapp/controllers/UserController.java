package com.mohak.bloggingapp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohak.bloggingapp.payloads.ApiResponse;
import com.mohak.bloggingapp.payloads.UserDto;
import com.mohak.bloggingapp.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	//Create User API
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//Update User API
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId")Integer uid)
	{
		UserDto updatedUser= this.userService.updateUser(userDto, uid);
		return  ResponseEntity.ok(updatedUser);
	}
	
	//Delete User API
	//Can use this way also
	/*@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId")Integer uid)
	{
		this.userService.Delete(uid);
		return new ResponseEntity(Map.of("message","User Deleted Succesfully"), HttpStatus.OK);
	}*/
	
	//Delete User API
		@DeleteMapping("/{userId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Integer uid)
		{
			this.userService.Delete(uid);
			return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succesfully", true), HttpStatus.OK);
		}

		//GET API - TO get all users
		@GetMapping("/")
		public ResponseEntity<List<UserDto>> getAllUsers()
		{
			return ResponseEntity.ok(this.userService.getAllUsers());
		}
		
		//GET API - TO get single user
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getUser(@PathVariable Integer userId)
		{
			return ResponseEntity.ok(this.userService.getUserById(userId));
		}
		
		@GetMapping("/current-user")
		public String  getLoggedInUser(Principal principal)
		{
			return principal.getName();		}
		
}
