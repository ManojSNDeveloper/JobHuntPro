package com.manoj.ojp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manoj.ojp.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> 
{
	List<Application> findByApplicantId(int seekerId);
	Optional<Application> findByJobIdAndApplicantId(int jobId, int applicantId);
	List<Application> findByJob_PostedBy_Id(int employerId);

	@Query("SELECT a FROM Application a WHERE a.job.postedBy.id = :employerId")
	List<Application> findByJobPostedById(@Param("employerId") int employerId);
}
