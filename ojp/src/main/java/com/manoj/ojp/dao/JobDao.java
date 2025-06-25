package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.repository.JobRepository;

@Repository
public class JobDao 
{
	@Autowired
	private JobRepository repo;
	
	public Job saveJob(Job job) 
	{ 
		 return repo.save(job); 
	}

	public List<Job> viewAll()  
	{
		return repo.findAll();
	}
	
	public Optional<Job> viewById(int id)
	{
		return repo.findById(id);
	}
 
	public Optional<Job> findById(int jobId) 
	{
		return repo.findById(jobId);
	}

	public Job updateJob(Job existingJob) 
	{
		return repo.save(existingJob);
	}

	public void deleteById(int id) 
	{
		repo.deleteById(id);
	}
	
	public List<Job> findByPostedById(int employerId) 
	{
		return repo.findByPostedById(employerId);
	}
	
	// ----------------------------------------------------------------------
	
	public List<Job> findByLocation(String location) 
	{
	    return repo.findByLocationIgnoreCase(location);
	}

	public List<Job> findByJobType(String jobType) {
	    return repo.findByJobTypeIgnoreCase(jobType);
	}

	public List<Job> findByExperience(String experience) {
	    return repo.findByExperienceContainingIgnoreCase(experience);
	}

	public List<Job> findBySkills(String skill) {
	    return repo.findBySkillsContainingIgnoreCase(skill);
	}

	public List<Job> findBySalary(String salary) {
	    return repo.findBySalary(salary);
	}


	
}
