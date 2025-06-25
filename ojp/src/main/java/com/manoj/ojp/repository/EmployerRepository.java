package com.manoj.ojp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manoj.ojp.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Integer>
{
//	@Query("SELECT s FROM StudentProfile s WHERE s.year LIKE %:year%")
//	List<StudentProfile> findByYear(@Param("year") String year);
//
//	@Query("SELECT s FROM StudentProfile s WHERE s.name LIKE %:name%")
//	List<StudentProfile> findByName(@Param("name") String string);
//
//	@Query("SELECT s FROM StudentProfile s WHERE LOWER(s.department.name) LIKE LOWER(CONCAT('%', :name, '%'))")
//	List<StudentProfile> findByBranch(@Param("name") String name);

	Optional<Employer> findByUserId(int userId);
	
}
