package com.manoj.ojp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dao.AdminDao;
import com.manoj.ojp.entity.Admin;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.UserRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.utility.Role;

@Service
public class AdminService 
{
	@Autowired
	private AdminDao dao;
	
	@Autowired
	private UserRepository userRepo;
	
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) 
	{
	    int userId = admin.getUser().getId();

	    // Load user and department from DB so they are managed entities
	    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Check role
	    if (user.getRole() != Role.ADMIN) {
	        throw new RuntimeException("User is not a Admin");
	    }
 
	    // Set managed references
	    admin.setUser(user);
	    
	    Admin saved = dao.saveAdmin(admin);

	    ResponseStructure rs = ResponseStructure.builder()
	        .status(HttpStatus.OK.value())
	        .message("Admin saved successfully")
	        .body(saved)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK.value()).body(rs);
	}
  
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(int id, Admin updatedAdmin) {
	    Optional<Admin> existingOpt = dao.viewById(id);
	    if (existingOpt.isEmpty()) {
	        throw new RuntimeException("Admin not found with ID: " + id);
	    }

	    Admin existingAdmin = existingOpt.get();

	    // Update fields
	    existingAdmin.setDepartment(updatedAdmin.getDepartment());

	    // Update User details if present
	    User updatedUser = updatedAdmin.getUser();
	    if (updatedUser != null) {
	        User user = existingAdmin.getUser(); // existing user reference
	        user.setName(updatedUser.getName());
	        user.setEmail(updatedUser.getEmail());
	        user.setPassword(updatedUser.getPassword());
	        userRepo.save(user); // persist updated user
	    }

	    Admin savedAdmin = dao.updateAdmin(existingAdmin);

	    ResponseStructure<Admin> rs = ResponseStructure.<Admin>builder()
	        .status(HttpStatus.OK.value())
	        .message("Admin updated successfully")
	        .body(savedAdmin)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	
	public ResponseEntity<ResponseStructure<List<Admin>>> viewAll() 
	{
	    List<Admin> allStudents = dao.viewAll();

	    ResponseStructure<List<Admin>> rs = ResponseStructure.<List<Admin>>builder()
	        .status(HttpStatus.OK.value())
	        .message("Admin Found Successfully")
	        .body(allStudents)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}


	public ResponseEntity<ResponseStructure<Optional<Admin>>> viewById(int id) 
	{
		Optional<Admin> byId = dao.viewById(id);
		if(byId.isEmpty())
		{
			throw new RuntimeException("Invalid Admin Id Unable to Find");
		}
		Admin profile = byId.get();
		
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Admin Found By ID Successfully").body(profile).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
}
