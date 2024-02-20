package com.ps20652.Hotel.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "room_images")
@Data
public class RoomImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "room_id")
     @JsonBackReference
    private Room room;

    @Column(name = "image_url")
    private String imageUrl;

  
}
