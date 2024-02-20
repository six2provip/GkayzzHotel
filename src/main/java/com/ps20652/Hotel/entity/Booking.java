package com.ps20652.Hotel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "bookings")
@Data
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Temporal(TemporalType.DATE)
    @Column(name = "checkin_date")
    private Date checkinDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "checkout_date")
    private Date checkoutDate;


}
