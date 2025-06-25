package com.manoj.ojp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.ojp.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>
{
	Optional<Admin> findByUserId(int userId);
}
