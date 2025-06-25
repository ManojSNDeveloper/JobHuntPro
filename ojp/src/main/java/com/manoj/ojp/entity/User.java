package com.manoj.ojp.entity;


import com.manoj.ojp.utility.Role;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; 
}