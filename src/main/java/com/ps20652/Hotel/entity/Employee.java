package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
}
