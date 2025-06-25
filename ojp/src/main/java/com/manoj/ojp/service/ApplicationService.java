package com.manoj.ojp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dao.ApplicationDao;
import com.manoj.ojp.entity.Application;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.entity.JobSeeker;
import com.manoj.ojp.repository.ApplicationRepository;
import com.manoj.ojp.repository.JobRepository;
import com.manoj.ojp.repository.JobSeekerRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.util.ApplicationStatus;
import com.manoj.ojp.utility.Role;

@Service
public class ApplicationService 
{

    @Autowired
    private ApplicationDao dao;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private JobSeekerRepository seekerRepo;

    public ResponseEntity<ResponseStructure<Application>> saveApplication(Application app) {
        Job job = jobRepo.findById(app.getJob().getId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobSeeker seeker = seekerRepo.findById(app.getApplicant().getId())
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        // ❗ Check if application already exists
        Optional<Application> existingApp = dao.findByJobIdAndApplicantId(job.getId(), seeker.getId());
        if (existingApp.isPresent()) {
            throw new RuntimeException("You have already applied for this job.");
        }

        app.setJob(job);
        app.setApplicant(seeker);
        app.setAppliedDate(LocalDate.now());
        app.setStatus(ApplicationStatus.APPLIED);

        Application saved = dao.save(app);

        ResponseStructure<Application> structure = new ResponseStructure<>();
        structure.setMessage("Application submitted");
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setBody(saved);

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }


    public ResponseEntity<ResponseStructure<Application>> updateApplication(int id, Application updated) {
        Application existing = dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        existing.setStatus(updated.getStatus());

        Application saved = dao.update(existing);

        ResponseStructure<Application> structure = new ResponseStructure<>();
        structure.setMessage("Application updated");
        structure.setStatus(HttpStatus.OK.value());
        structure.setBody(saved);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Application>>> getAllApplications() {
        List<Application> apps = dao.findAll();

        ResponseStructure<List<Application>> structure = new ResponseStructure<>();
        structure.setMessage("All applications fetched");
        structure.setStatus(HttpStatus.OK.value());
        structure.setBody(apps);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Application>> getApplicationById(int id) {
        Application app = dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ResponseStructure<Application> structure = new ResponseStructure<>();
        structure.setMessage("Application found");
        structure.setStatus(HttpStatus.OK.value());
        structure.setBody(app);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteApplication(int id) {
        Application app = dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        dao.delete(app);

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setMessage("Application deleted");
        structure.setStatus(HttpStatus.OK.value());
        structure.setBody("Deleted successfully");

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Application>>> getApplicationsBySeekerId(int seekerId) {
        List<Application> apps = dao.findByApplicantId(seekerId);

        ResponseStructure<List<Application>> structure = new ResponseStructure<>();
        structure.setMessage("Applications for seeker fetched");
        structure.setStatus(HttpStatus.OK.value());
        structure.setBody(apps);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
    
 // ------------------- update for Application by recruiter
    //--------------------------------------------------------------
    
    public ResponseEntity<ResponseStructure<List<Application>>> getApplicationsByEmployer(int employerId) {
        List<Application> applications = dao.findAllByEmployerId(employerId);

        ResponseStructure<List<Application>> rs = ResponseStructure.<List<Application>>builder()
                .status(HttpStatus.OK.value())
                .message("Applications fetched successfully")
                .body(applications)
                .build();

        return ResponseEntity.ok(rs);
    }

    public ResponseEntity<ResponseStructure<Application>> updateApplicationStatus(int appId, ApplicationStatus status) {
        Application app = dao.findById1(appId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + appId));

        app.setStatus(status);
        Application updated = dao.save1(app);

        ResponseStructure<Application> rs = ResponseStructure.<Application>builder()
                .status(HttpStatus.OK.value())
                .message("Application status updated successfully")
                .body(updated)
                .build();

        return ResponseEntity.ok(rs);
    }
    
}
