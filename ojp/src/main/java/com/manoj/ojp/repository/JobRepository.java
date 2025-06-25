package com.manoj.ojp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.ojp.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer>
{
	List<Job> findByLocationIgnoreCase(String location);
	List<Job> findByJobTypeIgnoreCase(String jobType);
	List<Job> findByExperienceContainingIgnoreCase(String experience);
	List<Job> findBySkillsContainingIgnoreCase(String skill); // partial match
	List<Job> findBySalary(String salary); // or use containing if needed
	List<Job> findByPostedById(int employerId);
	
}
