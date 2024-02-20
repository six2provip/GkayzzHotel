package com.ps20652.Hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@SuppressWarnings("serial")


@Entity
@Table(name = "room_types")
@Data
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

}
