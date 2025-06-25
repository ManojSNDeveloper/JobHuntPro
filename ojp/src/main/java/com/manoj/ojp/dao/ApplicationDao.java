package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.Application;
import com.manoj.ojp.entity.Job;
import com.manoj.ojp.repository.ApplicationRepository;
@Repository
public class ApplicationDao {

    @Autowired
    private ApplicationRepository repo;

    public Application save(Application app) {
        return repo.save(app);
    }

    public Application update(Application app) {
        return repo.save(app);
    }

    public List<Application> findAll() {
        return repo.findAll();
    }

    public Optional<Application> findById(int id) {
        return repo.findById(id);
    }

    public void delete(Application app) {
        repo.delete(app);
    }

    public List<Application> findByApplicantId(int seekerId) {
        return repo.findByApplicantId(seekerId);
    }

	public Optional<Application> findByJobIdAndApplicantId(int id, int id2) 
	{
		return repo.findByJobIdAndApplicantId(id, id2);
	}
	
	  // ------------------- update for Application by recruiter
    // ------------------------------------------------------------------------
    
	
	public Optional<Application> findById1(int id) {
	    return repo.findById(id);
	}

	public Application save1(Application app) {
	    return repo.save(app);
	}

	public List<Application> findAllByEmployerId(int employerId) {
	    return repo.findByJobPostedById(employerId); // if this is custom, make sure repo supports it
	}

	
}
