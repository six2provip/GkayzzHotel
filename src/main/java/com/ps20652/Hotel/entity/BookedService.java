package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "booked_services")
@Data
public class BookedService implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    // Getters and setters
}
