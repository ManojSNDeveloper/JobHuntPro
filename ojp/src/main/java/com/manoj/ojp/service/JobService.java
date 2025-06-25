package com.manoj.ojp.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dao.JobDao;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.EmployerRepository;
import com.manoj.ojp.repository.JobRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.utility.Role;

@Service
public class JobService 
{
	@Autowired
	private JobDao dao;
	
	@Autowired
	private EmployerRepository empRepo;
	
	@Autowired
	private JobRepository jobRepo;
	
	
	public ResponseEntity<ResponseStructure<Job>> saveJob(Job job) 
	{
	    int empId = job.getPostedBy().getId();

	    // Load emp from DB so they are managed entities
	    Employer emp = empRepo.findById(empId).orElseThrow(()-> new RuntimeException("Employer not found"));
	    
	    // Check role
	    if (emp.getUser().getRole() != Role.RECRUITER) {
	        throw new RuntimeException("User is not a RECRUITER");
	    }
 
	    // Set managed references
	    job.setPostedBy(emp);
	    
	    Job saved = dao.saveJob(job);

	    ResponseStructure rs = ResponseStructure.builder()
	        .status(HttpStatus.OK.value())
	        .message("Job saved successfully")
	        .body(saved)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK.value()).body(rs);
	}
   
	public ResponseEntity<ResponseStructure<Job>> updateJob(Job job) 
	{
	    int jobId = job.getId();

	    // Step 1: Fetch existing job by ID
	    Job existingJob = dao.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

	    // Step 2: Check employer & role
	    Employer emp = empRepo.findById(job.getPostedBy().getId())
	            .orElseThrow(() -> new RuntimeException("Employer not found"));

	    if (emp.getUser().getRole() != Role.RECRUITER) {
	        throw new RuntimeException("Only RECRUITER can update job");
	    }

	    // Step 3: Update fields
	    existingJob.setTitle(job.getTitle());
	    existingJob.setDescription(job.getDescription());
	    existingJob.setResponsibilities(job.getResponsibilities());
	    existingJob.setLocation(job.getLocation());
	    existingJob.setExperience(job.getExperience());
	    existingJob.setSkills(job.getSkills());
	    existingJob.setJobType(job.getJobType());
	    existingJob.setSalary(job.getSalary());
	    existingJob.setDeadline(job.getDeadline());
	    existingJob.setPostedBy(emp);  // Optional: Keep original if you don't allow changing employer

	    // Step 4: Save updated job
	    Job updatedJob = dao.updateJob(existingJob);  // Or dao.update(existingJob)

	    // Step 5: Return response
	    ResponseStructure<Job> rs = ResponseStructure.<Job>builder()
	            .status(HttpStatus.OK.value())
	            .message("Job updated successfully")
	            .body(updatedJob)
	            .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	
	public ResponseEntity<ResponseStructure<List<Job>>> viewAll() 
	{
	    List<Job> allStudents = dao.viewAll();

	    ResponseStructure<List<Job>> rs = ResponseStructure.<List<Job>>builder()
	        .status(HttpStatus.OK.value())
	        .message("Job Found Successfully")
	        .body(allStudents)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}


	public ResponseEntity<ResponseStructure<Optional<Job>>> viewById(int id) 
	{
		Optional<Job> byId = dao.viewById(id);
		if(byId.isEmpty())
		{
			throw new RuntimeException("Invalid Job Id Unable to Find");
		}
		Job profile = byId.get();
		
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Job Found By ID Successfully").body(profile).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteById(int id) 
	{
		Optional<Job> opt = dao.viewById(id); // viewById is above method
		if (opt.isEmpty()) {
			throw new RuntimeException("Invalid Job Id Unable to delete");
		}
		dao.deleteById(id);

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("Job Delete By ID Successfully").body("Job Deleted").build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
	
	public ResponseEntity<ResponseStructure<List<Job>>> getJobsByEmployerId(int employerId) {
	    List<Job> jobs = dao.findByPostedById(employerId);

	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found for Employer ID: " + employerId);
	    }

	    ResponseStructure<List<Job>> rs = ResponseStructure.<List<Job>>builder()
	            .status(HttpStatus.OK.value())
	            .message("Jobs retrieved successfully")
	            .body(jobs)
	            .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<Map<String, Object>>> getDashboardStats(int employerId) 
	{
	    List<Job> jobs = jobRepo.findByPostedById(employerId);

	    LocalDate today = LocalDate.now();

	    long total = jobs.size();
	    long active = jobs.stream()
	            .filter(job -> {
	                try {
	                    return LocalDate.parse(job.getDeadline()).isAfter(today) || LocalDate.parse(job.getDeadline()).isEqual(today);
	                } catch (Exception e) {
	                    return false;
	                }
	            }).count();

	    List<Map<String, String>> upcoming = jobs.stream()
	            .filter(job -> {
	                try {
	                    return LocalDate.parse(job.getDeadline()).isAfter(today);
	                } catch (Exception e) {
	                    return false;
	                }
	            })
	            .sorted(Comparator.comparing(job -> LocalDate.parse(job.getDeadline())))
	            .limit(5)
	            .map(job -> Map.of("title", job.getTitle(), "deadline", job.getDeadline()))
	            .collect(Collectors.toList());

	    Map<String, Object> stats = new HashMap<>();
	    stats.put("totalJobs", total);
	    stats.put("activeJobs", active);
	    stats.put("upcomingDeadlines", upcoming);

	    ResponseStructure<Map<String, Object>> structure = ResponseStructure.<Map<String, Object>>builder()
	            .status(HttpStatus.OK.value())
	            .message("Dashboard stats fetched")
	            .body(stats)
	            .build();

	    return ResponseEntity.ok(structure);
	}

	
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	
	public ResponseEntity<ResponseStructure<List<Job>>> findByLocation(String location) 
	{
	    List<Job> jobs = dao.findByLocation(location);

	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found for location: " + location);
	    }

	    ResponseStructure<List<Job>> rs = ResponseStructure.<List<Job>>builder()
	        .status(HttpStatus.OK.value())
	        .message("Jobs fetched by location successfully")
	        .body(jobs)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<List<Job>>> findByJobType(String jobType) {
	    List<Job> jobs = dao.findByJobType(jobType);
	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found with job type: " + jobType);
	    }
	    return wrapList("Jobs fetched by job type successfully", jobs);
	}

	public ResponseEntity<ResponseStructure<List<Job>>> findByExperience(String experience) {
	    List<Job> jobs = dao.findByExperience(experience);
	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found with experience: " + experience);
	    }
	    return wrapList("Jobs fetched by experience successfully", jobs);
	}

	public ResponseEntity<ResponseStructure<List<Job>>> findBySkills(String skill) {
	    List<Job> jobs = dao.findBySkills(skill);
	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found with skill: " + skill);
	    }
	    return wrapList("Jobs fetched by skill successfully", jobs);
	}

	public ResponseEntity<ResponseStructure<List<Job>>> findBySalary(String salary) {
	    List<Job> jobs = dao.findBySalary(salary);
	    if (jobs.isEmpty()) {
	        throw new RuntimeException("No jobs found with salary: " + salary);
	    }
	    return wrapList("Jobs fetched by salary successfully", jobs);
	}

	private ResponseEntity<ResponseStructure<List<Job>>> wrapList(String msg, List<Job> jobs) {
	    ResponseStructure<List<Job>> rs = ResponseStructure.<List<Job>>builder()
	        .status(HttpStatus.OK.value())
	        .message(msg)
	        .body(jobs)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}
	
	public ResponseEntity<ResponseStructure<List<Job>>> filterJobs(
	        String location, String jobType, String experience, String skills, String salary,
	        int page, int size) {

	    List<Job> allJobs = dao.viewAll();

	    List<Job> filtered = allJobs.stream()
	        .filter(job -> location == null || job.getLocation().equalsIgnoreCase(location))
	        .filter(job -> jobType == null || job.getJobType().equalsIgnoreCase(jobType))
	        .filter(job -> experience == null || job.getExperience().equalsIgnoreCase(experience))
	        .filter(job -> skills == null || job.getSkills().toLowerCase().contains(skills.toLowerCase()))
	        .filter(job -> salary == null || job.getSalary().equalsIgnoreCase(salary))
	        .toList();

	    if (filtered.isEmpty()) {
	        throw new RuntimeException("No jobs found matching the given criteria.");
	    }

	    int start = page * size;
	    int end = Math.min(start + size, filtered.size());
	    List<Job> pagedJobs = filtered.subList(start, end);

	    ResponseStructure<List<Job>> rs = ResponseStructure.<List<Job>>builder()
	            .status(HttpStatus.OK.value())
	            .message("Filtered jobs fetched successfully")
	            .body(pagedJobs)
	            .build();

	    return ResponseEntity.ok(rs);
	}


}
