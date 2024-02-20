package com.ps20652.Hotel.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@SuppressWarnings("serial")

@Entity
@Table(name = "rooms")
@Data
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "room_number")
    private Integer roomNumber;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomType roomType;
    
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RoomImage> roomImages = new ArrayList<>(); // Khởi tạo trực tiếp để tránh NullPointerException

    // public void addRoomImage(RoomImage roomImage) {
    // roomImages.add(roomImage);
    // roomImage.setRoom(this);
    // }

    // public void removeRoomImage(RoomImage roomImage) {
    // roomImages.remove(roomImage);
    // roomImage.setRoom(null);
    // }
}
