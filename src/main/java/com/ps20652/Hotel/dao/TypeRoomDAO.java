package com.ps20652.Hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ps20652.Hotel.entity.RoomType;

public interface TypeRoomDAO extends JpaRepository<RoomType, Long> {
    
}
