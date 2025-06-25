package com.manoj.ojp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/admin-about")
    public String adminAbout() {
        return "forward:/admin-about.html";
    }

    @GetMapping("/admin-admins")
    public String adminAdmins() {
        return "forward:/admin-admins.html";
    }

    @GetMapping("/admin-applications")
    public String adminApplications() {
        return "forward:/admin-applications.html";
    }

    @GetMapping("/admin-contact")
    public String adminContact() {
        return "forward:/admin-contact.html";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "forward:/admin-dashboard.html";
    }

    @GetMapping("/admin-index")
    public String adminIndex() {
        return "forward:/admin-index.html";
    }

    @GetMapping("/admin-jobseekers")
    public String adminJobseekers() {
        return "forward:/admin-jobseekers.html";
    }

    @GetMapping("/admin-login")
    public String adminLogin() {
        return "forward:/admin-login.html";
    }

    @GetMapping("/admin-profile")
    public String adminProfile() {
        return "forward:/admin-profile.html";
    }

    @GetMapping("/admin-recruiters")
    public String adminRecruiters() {
        return "forward:/admin-recruiters.html";
    }

    @GetMapping("/admin-register")
    public String adminRegister() {
        return "forward:/admin-register.html";
    }

    @GetMapping("/admin-report")
    public String adminReport() {
        return "forward:/admin-report.html";
    }

    @GetMapping("/apply-job")
    public String applyJob() {
        return "forward:/apply-job.html";
    }

    @GetMapping("/company-profile")
    public String companyProfile() {
        return "forward:/company-profile.html";
    }

    @GetMapping("/index")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/job-detail-admin")
    public String jobDetailAdmin() {
        return "forward:/job-detail-admin.html";
    }

    @GetMapping("/job-detail-jobseeker")
    public String jobDetailJobseeker() {
        return "forward:/job-detail-jobseeker.html";
    }

    @GetMapping("/job-detail-recruiter")
    public String jobDetailRecruiter() {
        return "forward:/job-detail-recruiter.html";
    }

    @GetMapping("/jobseeker-about")
    public String jobseekerAbout() {
        return "forward:/jobseeker-about.html";
    }

    @GetMapping("/jobseeker-application")
    public String jobseekerApplication() {
        return "forward:/jobseeker-application.html";
    }

    @GetMapping("/jobseeker-contact")
    public String jobseekerContact() {
        return "forward:/jobseeker-contact.html";
    }

    @GetMapping("/jobseeker-index")
    public String jobseekerIndex() {
        return "forward:/jobseeker-index.html";
    }

    @GetMapping("/jobseeker-login")
    public String jobseekerLogin() {
        return "forward:/jobseeker-login.html";
    }

    @GetMapping("/jobseeker-profile")
    public String jobseekerProfile() {
        return "forward:/jobseeker-profile.html";
    }

    @GetMapping("/jobseeker-register")
    public String jobseekerRegister() {
        return "forward:/jobseeker-register.html";
    }

    @GetMapping("/jobseeker-report")
    public String jobseekerReport() {
        return "forward:/jobseeker-report.html";
    }

    @GetMapping("/main-index")
    public String mainIndex() {
        return "forward:/main-index.html";
    }
    
    @GetMapping("/main-about")
    public String mainAbout() {
    	return "forward:/main-about.html";
    }
    
    @GetMapping("/main-contact")
    public String mainContact() {
    	return "forward:/main-contact.html";
    }
    
    @GetMapping("/main-report")
    public String mainReport() {
    	return "forward:/main-report.html";
    }

    @GetMapping("/main-login")
    public String mainLogin() {
        return "forward:/main-login.html";
    }

    @GetMapping("/main-register")
    public String mainRegister() {
        return "forward:/main-register.html";
    }

    @GetMapping("/my-applications")
    public String myApplications() {
        return "forward:/my-applications.html";
    }

    @GetMapping("/navbar")
    public String navbar() {
        return "forward:/navbar.html";
    }

    @GetMapping("/post-job")
    public String postJob() {
        return "forward:/post-job.html";
    }

    @GetMapping("/recruiter-about")
    public String recruiterAbout() {
        return "forward:/recruiter-about.html";
    }

    @GetMapping("/recruiter-application")
    public String recruiterApplication() {
        return "forward:/recruiter-application.html";
    }

    @GetMapping("/recruiter-contact")
    public String recruiterContact() {
        return "forward:/recruiter-contact.html";
    }

    @GetMapping("/recruiter-dashboard")
    public String recruiterDashboard() {
        return "forward:/recruiter-dashboard.html";
    }

    @GetMapping("/recruiter-index")
    public String recruiterIndex() {
        return "forward:/recruiter-index.html";
    }

    @GetMapping("/recruiter-login")
    public String recruiterLogin() {
        return "forward:/recruiter-login.html";
    }

    @GetMapping("/recruiter-profile")
    public String recruiterProfile() {
        return "forward:/recruiter-profile.html";
    }

    @GetMapping("/recruiter-register")
    public String recruiterRegister() {
        return "forward:/recruiter-register.html";
    }

    @GetMapping("/recruiter-report")
    public String recruiterReport() {
        return "forward:/recruiter-report.html";
    }

    @GetMapping("/update-job")
    public String updateJob() {
        return "forward:/update-job.html";
    }

    @GetMapping("/view-applications")
    public String viewApplications() {
        return "forward:/view-applications.html";
    }
}
