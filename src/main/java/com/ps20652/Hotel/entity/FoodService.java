package com.ps20652.Hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "food_services")
@Data
public class FoodService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "service_id")
    private Service service;

}
