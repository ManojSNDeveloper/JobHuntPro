package com.manoj.ojp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manoj.ojp.dto.LoginRequestDTO;
import com.manoj.ojp.dto.LoginResponseDTO;
import com.manoj.ojp.entity.Admin;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.JobSeeker;
import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.AdminRepository;
import com.manoj.ojp.repository.EmployerRepository;
import com.manoj.ojp.repository.JobSeekerRepository;
import com.manoj.ojp.repository.UserRepository;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.utility.Role;

@Service
public class AuthService 
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private EmployerRepository empRepo;

    @Autowired
    private AdminRepository adminRepo;

    public ResponseEntity<ResponseStructure<LoginResponseDTO>> login(LoginRequestDTO loginRequest) {
        // 1. Find user by email
        Optional<User> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        // 2. Check password
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Prepare response
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login Successful");

        // 4. Load role-based profile using User ID
        switch (user.getRole()) {
            case JOB_SEEKER:
                JobSeeker jobSeeker = jobSeekerRepo.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));
                response.setProfile(jobSeeker);
                break;

            case RECRUITER:
                Employer employer = empRepo.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));
                response.setProfile(employer);
                break;

            case ADMIN:
                Admin admin = adminRepo.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Admin profile not found"));
                response.setProfile(admin);
                break;

            default:
                throw new RuntimeException("Unknown role: " + user.getRole());
        }

        // 5. Return structured response
        ResponseStructure<LoginResponseDTO> rs = ResponseStructure.<LoginResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User logged in successfully")
                .body(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> adminLogin(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied: Not an Admin");
        }

        Admin admin = adminRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Admin profile not found"));

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Admin login successful");
        response.setProfile(admin);

        ResponseStructure<LoginResponseDTO> rs = ResponseStructure.<LoginResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Admin logged in successfully")
                .body(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    
    
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> jobSeekerLogin(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException("Access denied: Not a Job Seeker");
        }

        JobSeeker jobSeeker = jobSeekerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Job Seeker profile not found"));

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Job Seeker login successful");
        response.setProfile(jobSeeker);

        ResponseStructure<LoginResponseDTO> rs = ResponseStructure.<LoginResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Job Seeker logged in successfully")
                .body(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    public ResponseEntity<ResponseStructure<LoginResponseDTO>> recruiterLogin(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepo.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Access denied: Not a Recruiter");
        }

        Employer employer = empRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Recruiter login successful");
        response.setProfile(employer);

        ResponseStructure<LoginResponseDTO> rs = ResponseStructure.<LoginResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Recruiter logged in successfully")
                .body(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
}
