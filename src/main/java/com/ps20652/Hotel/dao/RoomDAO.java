package com.ps20652.Hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20652.Hotel.entity.Room;

public interface RoomDAO extends JpaRepository<Room, Long> {
    
    boolean existsByRoomNumber(Integer roomNummber);


}
