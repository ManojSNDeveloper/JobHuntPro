package com.manoj.ojp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.EmployerService;

@RestController
@RequestMapping("/employer")
public class EmployerController 
{
	@Autowired
	private EmployerService service ;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Employer>> saveEmployer(@RequestBody Employer emp)
	{
		return service.saveEmployer(emp); 
	}
	 
	@PutMapping("/employer/update/{id}")
	public ResponseEntity<ResponseStructure<Employer>> updateEmployer(
	        @PathVariable int id,
	        @RequestBody Employer updatedEmployer) {
	    return service.updateEmployer(id, updatedEmployer);
	}

	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Employer>>> viewAll()
	{
		return service.viewAll();  
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<Employer>>> viewById(@PathVariable int id)
	{ 
		return service.viewById(id);
	}
}
