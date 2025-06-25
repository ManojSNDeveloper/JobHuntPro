package com.manoj.ojp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manoj.ojp.entity.User;
import com.manoj.ojp.repository.UserRepository;

@Repository
public class UserDao 
{
	@Autowired 
	private UserRepository repo;
	
	public User saveUser(User user) 
	{
		return repo.save(user);
	}

	public List<User> viewAll() 
	{
		return repo.findAll();
	}

	public Optional<User> viewById(int id) 
	{
		return repo.findById(id);
	}

	public User updateUser(User user) 
	{
		return repo.save(user);
	}

	public User updateById(User existingUser) 
	{
		return repo.save(existingUser);
	}
}
