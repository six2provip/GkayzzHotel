package com.ps20652.Hotel.services;

import java.util.List;

import com.ps20652.Hotel.entity.Room;
import com.ps20652.Hotel.entity.RoomType;

public interface RoomTypeService {

    public List<RoomType> findAll();

	// public Room create(RoomDTO roomDTO) ;

    public RoomType findbyId(Long id);

} 
    
