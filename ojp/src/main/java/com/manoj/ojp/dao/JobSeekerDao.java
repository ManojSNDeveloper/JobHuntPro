package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.entity.JobSeeker;
import com.manoj.ojp.repository.JobSeekerRepository;
import com.manoj.ojp.repository.UserRepository;

@Repository
public class JobSeekerDao 
{
    @Autowired
    private JobSeekerRepository repo;

    public JobSeeker saveJobSeeker(JobSeeker jobSeeker) 
    {
        return repo.save(jobSeeker);
    }
    
    public JobSeeker updateJobSeeker(JobSeeker jobSeeker) 
    {
    	return repo.save(jobSeeker);
    }

    public Optional<JobSeeker> findByUserId(int userId) 
    {
        return repo.findByUserId(userId);
    }
    
    public List<JobSeeker> viewAll()  
	{
		return repo.findAll();
	}
	
	public Optional<JobSeeker> viewById(int id) 
	{
		return repo.findById(id);
	}
	
	public void deleteById(int id) {
	    repo.deleteById(id);
	}

}
 