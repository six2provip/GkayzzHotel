package com.ps20652.Hotel.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    

}
