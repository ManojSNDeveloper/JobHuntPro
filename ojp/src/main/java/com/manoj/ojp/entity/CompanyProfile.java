package com.manoj.ojp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "company_profiles")
@Entity
public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private String address;
    private String industry;

    @OneToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;
}
