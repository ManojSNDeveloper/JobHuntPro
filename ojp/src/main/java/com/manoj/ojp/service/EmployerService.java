package com.manoj.ojp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dao.EmployerDao;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.UserRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.utility.Role;


@Service
public class EmployerService 
{
	@Autowired
	private EmployerDao dao;
	
	@Autowired
	private UserRepository userRepo;
	
	public ResponseEntity<ResponseStructure<Employer>> saveEmployer(Employer emp) 
	{
	    int userId = emp.getUser().getId();

	    // Load user and department from DB so they are managed entities
	    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Check role
	    if (user.getRole() != Role.RECRUITER) {
	        throw new RuntimeException("User is not a RECRUITER");
	    }
 
	    // Set managed references
	    emp.setUser(user);
	    
	    Employer saved = dao.saveEmp(emp);

	    ResponseStructure rs = ResponseStructure.builder()
	        .status(HttpStatus.OK.value())
	        .message("Employer saved successfully")
	        .body(saved)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK.value()).body(rs);
	}
 
	public ResponseEntity<ResponseStructure<Employer>> updateEmployer(int id, Employer updatedEmployer) 
	{
	    Employer existingEmployer = dao.findById(id)
	            .orElseThrow(() -> new RuntimeException("Employer not found"));

	    User user = existingEmployer.getUser();
	    if (user.getRole() != Role.RECRUITER) {
	        throw new RuntimeException("User is not a RECRUITER");
	    }

	    // Update fields
	    existingEmployer.setCompanyName(updatedEmployer.getCompanyName());
	    existingEmployer.setWebsite(updatedEmployer.getWebsite());
	    existingEmployer.setContactInfo(updatedEmployer.getContactInfo());
	    existingEmployer.setLogoUrl(updatedEmployer.getLogoUrl());

	    Employer saved = dao.saveEmp(existingEmployer);

	    ResponseStructure<Employer> rs = ResponseStructure.<Employer>builder()
	            .status(HttpStatus.OK.value())
	            .message("Employer updated successfully")
	            .body(saved)
	            .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}


//	
	public ResponseEntity<ResponseStructure<List<Employer>>> viewAll() 
	{
	    List<Employer> allStudents = dao.viewAll();

	    ResponseStructure<List<Employer>> rs = ResponseStructure.<List<Employer>>builder()
	        .status(HttpStatus.OK.value())
	        .message("Employer Found Successfully")
	        .body(allStudents)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}


	public ResponseEntity<ResponseStructure<Optional<Employer>>> viewById(int id) 
	{
		Optional<Employer> byId = dao.viewById(id);
		if(byId.isEmpty())
		{
			throw new RuntimeException("Invalid Employer Id Unable to Find");
		}
		Employer profile = byId.get();
		
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Employer Found By ID Successfully").body(profile).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
//
//
//	public ResponseEntity<ResponseStructure<String>> deleteById(int id) 
//	{
//		Optional<StudentProfile> opt = dao.viewById(id); // viewById is above method
//		if(opt.isEmpty())
//		{
//			throw new RuntimeException("Invalid Student Id Unable to delete");
//		}
//		dao.deleteStudentById(id);
//		
//		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Student Delete By ID Successfully").body("Student Profile Deleted").build();
//		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
//		return re;
//	}
//
//
//	public ResponseEntity<ResponseStructure<List<StudentProfile>>> findByYear(String year) 
//	{
//		List<StudentProfile> el = dao.findByYear(year);
//		ResponseStructure st = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Student Found Successfully").body(el).build();
//		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(st);
//		return re;
//	}
//
//
//	public ResponseEntity<ResponseStructure<List<StudentProfile>>> findByName(String name) 
//	{
//		List<StudentProfile> el = dao.findByName(name);
//		ResponseStructure st = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Student Found Successfully").body(el).build();
//		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(st);
//		return re;
//	}
//
//
//	public ResponseEntity<ResponseStructure<List<FacultyProfileDTO>>> viewAllFacultyAdvisors() 
//	{
//		List<FacultyProfile> allFaculty = dao.viewAllFacultyAdvisors();
//
//	    List<FacultyProfileDTO> dtos = allFaculty.stream().map(faculty -> 
//	    { 
//	     // Convert Department
//	     Department dept = faculty.getDepartment();
//	     DepartmentDTO  deptDTO = new DepartmentDTO(dept.getId(), dept.getName());
//	     
//	      
//    
//	     // Build StudentProfileDTO 
//	     return new FacultyProfileDTO( 
//	            faculty.getName(),
//	            deptDTO, 
//	            faculty.getEmail(),
//	            faculty.getPhone() 
//	        );
//	    }).collect(Collectors.toList());
//
//	    ResponseStructure<List<FacultyProfileDTO>> rs = ResponseStructure.<List<FacultyProfileDTO>>builder()
//	        .status(HttpStatus.OK.value())
//	        .message("Faculty Found Successfully")
//	        .body(dtos)
//	        .build();
//
//	    return ResponseEntity.status(HttpStatus.OK).body(rs);
//	}
//
//
//	public ResponseEntity<ResponseStructure<List<StudentProfile>>> findByBranch(String branch) 
//	{
//		List<StudentProfile> el = dao.findByBranch(branch);
//		ResponseStructure st = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Student Found Successfully").body(el).build();
//		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(st);
//		return re;
//	}
//
//
//
//	/*
//	 	📦 Summary of Objects Used
//		Object								Purpose
//		StudentProfile					Entity fetched from DB
//		StudentProfileDTO				Data Transfer Object sent to the frontend
//		Department						Entity (part of student)
//		DepartmentDTO					Simplified version for the frontend
//		Course							Entity (part of student)
//		CourseDTO						Simplified version for the frontend
//		ResponseStructure<T>			Custom wrapper for consistent API responses
//		ResponseEntity					Spring’s standard way to return HTTP responses
//	 */

}
