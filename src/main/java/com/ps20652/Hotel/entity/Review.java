package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "reviews")
@Data
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    // Getters and setters
}
