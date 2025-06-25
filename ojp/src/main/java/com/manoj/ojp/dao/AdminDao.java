package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.Admin;
import com.manoj.ojp.entity.Employer;
import com.manoj.ojp.repository.AdminRepository;

@Repository
public class AdminDao 
{
	@Autowired
	private AdminRepository repo;
	
	public Admin saveAdmin(Admin admin) 
	{ 
		 return repo.save(admin);
	}

	public List<Admin> viewAll()  
	{
		return repo.findAll();
	}
	
	public Optional<Admin> viewById(int id) 
	{
		return repo.findById(id);
	}

	public Admin updateAdmin(Admin existingAdmin) 
	{
		 return repo.save(existingAdmin);
	}
}
