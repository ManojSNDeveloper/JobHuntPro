package com.manoj.ojp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manoj.ojp.entity.JobSeeker;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> 
{
	Optional<JobSeeker> findByUserId(int userId);
}
