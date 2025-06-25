package com.manoj.ojp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.ojp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> 
{
	Optional<User> findByEmail(String email);
}
