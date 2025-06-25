package com.manoj.ojp.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.ojp.dao.JobSeekerDao;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.JobSeeker;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.UserRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.utility.Role;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerDao dao;

    @Autowired
    private UserRepository userRepo;

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public ResponseEntity<?> saveJobSeekerWithResume(JobSeeker jobSeeker, MultipartFile file) {
        try {
            int userId = jobSeeker.getUser().getId();
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getRole() != Role.JOB_SEEKER) {
                throw new RuntimeException("User is not a JOB_SEEKER");
            }

            // Upload resume file
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            String resumeUrl = "/resumes/" + fileName;
            jobSeeker.setResumeUrl(resumeUrl);
            jobSeeker.setUser(user);

            JobSeeker saved = dao.saveJobSeeker(jobSeeker);

            ResponseStructure<JobSeeker> rs = ResponseStructure.<JobSeeker>builder()
                    .status(HttpStatus.OK.value())
                    .message("Job Seeker and Resume saved successfully")
                    .body(saved)
                    .build();

            return ResponseEntity.status(HttpStatus.OK.value()).body(rs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
    
    public ResponseEntity<?> updateJobSeekerWithResume(JobSeeker updated, MultipartFile file) 
    {
    	try {
    		int seekerId = updated.getId();
    		JobSeeker existing = dao.viewById(seekerId)
    				.orElseThrow(() -> new RuntimeException("JobSeeker not found"));
    		
    		// Retain user and role
    		User user = userRepo.findById(existing.getUser().getId())
    				.orElseThrow(() -> new RuntimeException("User not found"));
    		
    		if (user.getRole() != Role.JOB_SEEKER) {
    			throw new RuntimeException("Only JOB_SEEKER can update profile");
    		}
    		
    		existing.setContact(updated.getContact());
    		existing.setEducation(updated.getEducation());
    		existing.setExperience(updated.getExperience());
    		existing.setSkills(updated.getSkills());
    		
    		if (file != null && !file.isEmpty()) {
    			// Replace resume file
    			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    			Path filePath = Paths.get(UPLOAD_DIR + fileName);
    			Files.createDirectories(filePath.getParent());
    			Files.write(filePath, file.getBytes());
    			existing.setResumeUrl("/resumes/" + fileName);
    		}
    		
    		JobSeeker saved = dao.updateJobSeeker(existing);
    		
    		ResponseStructure<JobSeeker> rs = ResponseStructure.<JobSeeker>builder()
    				.status(HttpStatus.OK.value())
    				.message("JobSeeker updated successfully")
    				.body(saved)
    				.build();
    		
    		return ResponseEntity.ok(rs);
    	} 
    	catch (Exception e) 
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    	}
    }
    
    
	public ResponseEntity<ResponseStructure<List<JobSeeker>>> viewAll() 
	{
	    List<JobSeeker> allStudents = dao.viewAll();

	    ResponseStructure<List<JobSeeker>> rs = ResponseStructure.<List<JobSeeker>>builder()
	        .status(HttpStatus.OK.value())
	        .message("JobSeeker Found Successfully")
	        .body(allStudents)
	        .build();

	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}


	public ResponseEntity<ResponseStructure<Optional<JobSeeker>>> viewById(int id) 
	{
		Optional<JobSeeker> byId = dao.viewById(id);
		if(byId.isEmpty())
		{
			throw new RuntimeException("Invalid JobSeeker Id Unable to Find");
		}
		JobSeeker profile = byId.get();
		
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("Employer Found By ID Successfully").body(profile).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK.value()).body(rs);
		return re;
	}
	

	public ResponseEntity<ResponseStructure<String>> deleteJobSeeker(int id) {
	    Optional<JobSeeker> jobSeeker = dao.viewById(id);
	    if (jobSeeker.isEmpty()) {
	        throw new RuntimeException("Invalid JobSeeker ID");
	    }

	    dao.deleteById(id);

	    ResponseStructure<String> rs = ResponseStructure.<String>builder()
	            .status(HttpStatus.OK.value())
	            .message("JobSeeker deleted successfully")
	            .body("Deleted JobSeeker ID: " + id)
	            .build();

	    return ResponseEntity.ok(rs);
	}

}
