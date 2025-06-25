package com.manoj.ojp.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
@Entity
public class Job 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String Responsibilities;
    private String location;
    private String experience;
    private String skills;
    private String jobType;
    private String salary;
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer postedBy; 
}