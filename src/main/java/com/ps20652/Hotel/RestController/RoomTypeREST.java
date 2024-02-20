package com.ps20652.Hotel.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ps20652.Hotel.DTO.RoomDTO;
import com.ps20652.Hotel.entity.Room;
import com.ps20652.Hotel.entity.RoomType;
import com.ps20652.Hotel.services.RoomService;
import com.ps20652.Hotel.services.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeREST {

    @Autowired
    private RoomTypeService roomTypeService;

    // Get all rooms
    @GetMapping
    public ResponseEntity<List<RoomType>> getAllRoomTypes() {
        List<RoomType> roomType = roomTypeService.findAll();
        return new ResponseEntity<>(roomType, HttpStatus.OK);
    }

    // @PostMapping("/create")
    // public ResponseEntity<Room> createRoom(@RequestBody RoomDTO roomDTO) {
    //     Room createdRoom = roomService.create(roomDTO);
    //     return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    // }

}
