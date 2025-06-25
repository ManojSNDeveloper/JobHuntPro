package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.repository.EmployerRepository;
import com.manoj.ojp.repository.UserRepository;


@Repository
public class EmployerDao 
{
	@Autowired
	private EmployerRepository repo;
	
	public Employer saveEmp(Employer emp) 
	{ 
		 return repo.save(emp);
	}

	public Optional<Employer> findById(int id) // for PUT 
	{
	    return repo.findById(id);
	}

 
	public List<Employer> viewAll()  
	{
		return repo.findAll();
	}
	
	public Optional<Employer> viewById(int id) 
	{
		return repo.findById(id);
	}

 
// 
//	public User viewFacDetails() 
//	{
//		return (User) repo.findAll();
//	}
// 
//
//	public void deleteStudentById(int id) 
//	{
//		repo.deleteById(id);
//	}
//
//	public List<StudentProfile> findByYear(String year) 
//	{
//		return repo.findByYear(year + "%"); 
//	}
//
//	public List<StudentProfile> findByName(String name) 
//	{
//		return repo.findByName(name + "%"); 
//	}
//
//	public List<FacultyProfile> viewAllFacultyAdvisors() 
//	{
//		return facRepo.findAll();
//	}
//
//	public List<StudentProfile> findByBranch(String branch) 
//	{
//		return repo.findByBranch(branch + "%"); 
//	}
}
