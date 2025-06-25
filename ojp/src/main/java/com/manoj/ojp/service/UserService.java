package com.manoj.ojp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dao.UserDao;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.response_structure.ResponseStructure;

@Service
public class UserService 
{
	@Autowired
	private UserDao dao;
	
	public ResponseEntity<ResponseStructure<User>> saveUser(User user) 
	{  
		User save = dao.saveUser(user);
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("User Saved Successfully").body(save).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
	
	public ResponseEntity<ResponseStructure<User>> updateUser(User user) {
	    int userId = user.getId();

	    // Step 1: Fetch existing user
	    User existingUser = dao.viewById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    // Step 2: Update allowed fields only (DO NOT change role)
	    existingUser.setName(user.getName());
	    existingUser.setEmail(user.getEmail());
	    existingUser.setPassword(user.getPassword());  // Optional: encrypt if needed

	    // Step 3: Save updated user
	    User updatedUser = dao.updateUser(existingUser);

	    // Step 4: Return response
	    ResponseStructure<User> rs = ResponseStructure.<User>builder()
	            .status(HttpStatus.OK.value())
	            .message("User updated successfully")
	            .body(updatedUser)
	            .build();

	    return ResponseEntity.ok(rs);
	}
	
	public ResponseEntity<ResponseStructure<User>> updateUser(int id, User updatedUser) {
        User existingUser = dao.viewById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole()); // optional, or ignore if you don't allow role change

        User savedUser = dao.updateById(existingUser);

        ResponseStructure<User> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("User updated successfully");
        structure.setBody(savedUser);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }


	public ResponseEntity<ResponseStructure<List<User>>> viewAll() 
	{
		List<User> all = dao.viewAll();
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("All User Found Successfully").body(all).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
 
	public ResponseEntity<ResponseStructure<Optional<User>>> viewById(int id) 
	{
		Optional<User> byId = dao.viewById(id);
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("USer Found Successfully By Id").body(byId).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}

//	public String changePassword(int userId, String oldPassword, String newPassword) 
//	{
//	    Optional<User> optionalUser = dao.viewById(userId);
//	    if (optionalUser.isEmpty()) {
//	        throw new RuntimeException("User not found with ID: " + userId);
//	    }
//
//	    User user = optionalUser.get();
//
//	    if (!user.getPassword().equals(oldPassword)) {
//	        return "Old password is incorrect.";
//	    }
//
//	    user.setPassword(newPassword);
//	    dao.save(user);
//	    return "Password updated successfully.";
//	}
}
