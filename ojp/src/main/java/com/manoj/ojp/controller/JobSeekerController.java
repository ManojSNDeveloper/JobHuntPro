package com.manoj.ojp.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.JobSeeker;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.EmployerService;
import com.manoj.ojp.service.JobSeekerService;

@RestController
@RequestMapping("/jobseeker")
@CrossOrigin
public class JobSeekerController 
{

    @Autowired
    private JobSeekerService service;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveJobSeekerWithResume(
            @RequestPart("jobSeeker") JobSeeker jobSeeker,
            @RequestPart("resume") MultipartFile resumeFile) {
        return service.saveJobSeekerWithResume(jobSeeker, resumeFile);
    }
    
	@GetMapping
	public ResponseEntity<ResponseStructure<List<JobSeeker>>> viewAll()
	{
		return service.viewAll();  
	}
	 
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<JobSeeker>>> viewById(@PathVariable int id)
	{ 
		return service.viewById(id);
	}
	
	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateJobSeekerWithResume(
	        @RequestPart("jobSeeker") JobSeeker jobSeeker,
	        @RequestPart(value = "resume", required = false) MultipartFile resumeFile) 
	{
	    return service.updateJobSeekerWithResume(jobSeeker, resumeFile);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteJobSeeker(@PathVariable int id) {
	    return service.deleteJobSeeker(id);
	}

	
}
