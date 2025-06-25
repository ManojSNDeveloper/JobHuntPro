package com.manoj.ojp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ojp.entity.User;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController 
{
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user)
	{
		return service.saveUser(user);
	}
	
//	@PutMapping("/update")
//	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user)
//	{
//		return service.updateUser(user);
//	}
	
	@PutMapping("/update/{id}")
    public ResponseEntity<ResponseStructure<User>> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return service.updateUser(id, updatedUser);
    }
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<User>>> viewAll()
	{
		return service.viewAll();
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<User>>> viewById(@PathVariable int id)
	{
		return service.viewById(id);
	}
	 
//	@PutMapping("/change-password/{userId}")
//	public ResponseEntity<String> changePassword(@PathVariable int userId, @RequestBody PasswordChangeRequest request) 
//	{
//	    String result = service.changePassword(userId, request.getOldPassword(), request.getNewPassword());
//	    return ResponseEntity.ok(result);
//	}
}
