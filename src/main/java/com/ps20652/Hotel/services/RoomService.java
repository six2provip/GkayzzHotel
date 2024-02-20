package com.ps20652.Hotel.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ps20652.Hotel.DTO.RoomDTO;
import com.ps20652.Hotel.entity.Room;

public interface RoomService {

    public Room findbyId(Long id);

    // public List<Room> findAll();

    Page<Room> findAll(Pageable pageable);

	public Room create(RoomDTO roomDTO,  List<MultipartFile> roomImages) ;

    Room update(Long roomId, RoomDTO roomDTO, List<MultipartFile> roomImages);

} 
    
