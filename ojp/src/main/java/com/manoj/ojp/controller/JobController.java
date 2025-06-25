package com.manoj.ojp.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController 
{
	@Autowired
	private JobService service;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Job>> saveJob(@RequestBody Job job)
	{
		return service.saveJob(job); 
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Job>> updateJob(@RequestBody Job job) 
	{
		return service.updateJob(job);
	}
	  
	@GetMapping("/view-all")
	public ResponseEntity<ResponseStructure<List<Job>>> viewAll()
	{
		return service.viewAll();  
	}
	
	@GetMapping("/view-by-id/{id}")
	public ResponseEntity<ResponseStructure<Optional<Job>>> viewById(@PathVariable int id)
	{ 
		return service.viewById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable int id) {
		return service.deleteById(id);
	}
	
	@GetMapping("/view-by-employer/{employerId}")
	public ResponseEntity<ResponseStructure<List<Job>>> getJobsByEmployer(@PathVariable int employerId) {
	    return service.getJobsByEmployerId(employerId);
	}
	
	@GetMapping("/dashboard/{employerId}")
	public ResponseEntity<ResponseStructure<Map<String, Object>>> getRecruiterDashboard(@PathVariable int employerId) {
	    return service.getDashboardStats(employerId);
	}


	
	// ----------------------------------------------------------------------------------------
	
	@GetMapping("/location/{location}")
	public ResponseEntity<ResponseStructure<List<Job>>> getJobsByLocation(@PathVariable String location) {
	    return service.findByLocation(location);
	}

	@GetMapping("/jobtype/{jobType}")
	public ResponseEntity<ResponseStructure<List<Job>>> findByJobType(@PathVariable String jobType) {
	    return service.findByJobType(jobType);
	}

	@GetMapping("/experience/{experience}")
	public ResponseEntity<ResponseStructure<List<Job>>> findByExperience(@PathVariable String experience) {
	    return service.findByExperience(experience);
	}

	@GetMapping("/skills/{skill}")
	public ResponseEntity<ResponseStructure<List<Job>>> findBySkills(@PathVariable String skill) {
	    return service.findBySkills(skill);
	}

	@GetMapping("/salary/{salary}")
	public ResponseEntity<ResponseStructure<List<Job>>> findBySalary(@PathVariable String salary) {
	    return service.findBySalary(salary);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<ResponseStructure<List<Job>>> filterJobs(
	        @RequestParam(required = false) String location,
	        @RequestParam(required = false) String jobType,
	        @RequestParam(required = false) String experience,
	        @RequestParam(required = false) String skills,
	        @RequestParam(required = false) String salary,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	    return service.filterJobs(location, jobType, experience, skills, salary, page, size);
	}

}

	
	
	/*
	 ✅ Example: How Companies Do It
1. Create Exception Class
 
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

2. Use in Service Layer

 
if (jobs.isEmpty()) {
    throw new ResourceNotFoundException("No jobs found for type: " + jobType);
}

3. Handle in @ControllerAdvice

 
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleNotFound(ResourceNotFoundException ex) {
        ResponseStructure<String> rs = ResponseStructure.<String>builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Resource Not Found")
            .body(ex.getMessage())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
    }
}

🧠 Conclusion
In IT companies:

* Custom exceptions + Global exception handler is a standard best practice.
* It improves code quality, reusability, and clarity.
* You’re learning the correct way—switching from RuntimeException to custom exceptions is the right move if you're aiming for professional standards.
* Let me know if you'd like help refactoring your current RuntimeException usage to custom exceptions.
	 
	 */


