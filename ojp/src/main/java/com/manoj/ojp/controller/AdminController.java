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

import com.manoj.ojp.entity.Admin;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	private AdminService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody Admin admin)
	{
		return service.saveAdmin(admin); 
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(@PathVariable int id, @RequestBody Admin updatedAdmin) {
	    return service.updateAdmin(id, updatedAdmin);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Admin>>> viewAll()
	{
		return service.viewAll();  
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<Admin>>> viewById(@PathVariable int id)
	{ 
		return service.viewById(id);
	}
}