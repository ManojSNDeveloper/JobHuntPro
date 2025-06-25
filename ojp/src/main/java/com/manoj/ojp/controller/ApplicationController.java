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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ojp.entity.Application;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.ApplicationService;
import com.manoj.ojp.util.ApplicationStatus;
 

@RestController
@RequestMapping("/application")
public class ApplicationController 
{
    @Autowired
    private ApplicationService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<Application>> save(@RequestBody Application application) {
        return service.saveApplication(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Application>> update(@PathVariable int id, @RequestBody Application app) {
        return service.updateApplication(id, app);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Application>>> findAll() {
        return service.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Application>> findById(@PathVariable int id) {
        return service.getApplicationById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(@PathVariable int id) {
        return service.deleteApplication(id);
    }

    @GetMapping("/view-by-seeker/{seekerId}")
    public ResponseEntity<ResponseStructure<List<Application>>> findBySeeker(@PathVariable int seekerId) {
        return service.getApplicationsBySeekerId(seekerId);
    }
    
    // ------------------- update for Application by recruiter
    // ------------------------------------------------------------------------
 // Get all applications for jobs posted by a recruiter (employer)
    @GetMapping("/by-employer/{employerId}")
    public ResponseEntity<ResponseStructure<List<Application>>> getApplicationsByEmployer(@PathVariable int employerId) {
        return service.getApplicationsByEmployer(employerId);
    }

    // Update application status (SHORTLISTED, REJECTED, etc.)
    @PutMapping("/update-status/{applicationId}")
    public ResponseEntity<ResponseStructure<Application>> updateApplicationStatus(
            @PathVariable int applicationId,
            @RequestParam("status") ApplicationStatus status) {
        return service.updateApplicationStatus(applicationId, status);
    }
    
}
