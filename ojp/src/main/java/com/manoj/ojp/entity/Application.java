package com.manoj.ojp.entity;

import java.time.LocalDate;

import com.manoj.ojp.util.ApplicationStatus;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "applications",
uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "seeker_id"}) )
@Entity
public class Application 
{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate appliedDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seeker_id")
    private JobSeeker applicant;
}