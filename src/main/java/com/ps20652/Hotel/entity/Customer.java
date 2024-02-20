package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "customers")
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // @OneToMany(mappedBy = "customer")
    // private List<OTP> otps;
    
   


}
