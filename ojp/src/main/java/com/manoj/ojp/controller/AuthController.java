package com.manoj.ojp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.ojp.dto.LoginRequestDTO;
import com.manoj.ojp.dto.LoginResponseDTO;
import com.manoj.ojp.response_structure.ResponseStructure;
import com.manoj.ojp.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController 
{
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) 
    {
        return authService.login(request);
    }
    
    @PostMapping("/admin-login")
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> adminLogin(@RequestBody LoginRequestDTO request) {
        return authService.adminLogin(request); // only for ADMIN
    }
    
    @PostMapping("/jobseeker-login")
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> jobSeekerLogin(@RequestBody LoginRequestDTO request) {
        return authService.jobSeekerLogin(request);
    }

    @PostMapping("/recruiter-login")
    public ResponseEntity<ResponseStructure<LoginResponseDTO>> recruiterLogin(@RequestBody LoginRequestDTO request) {
        return authService.recruiterLogin(request);
    }

}
