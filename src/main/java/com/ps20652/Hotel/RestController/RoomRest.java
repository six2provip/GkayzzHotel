package com.ps20652.Hotel.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps20652.Hotel.DTO.RoomDTO;
import com.ps20652.Hotel.dao.RoomDAO;
import com.ps20652.Hotel.entity.Room;
import com.ps20652.Hotel.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomRest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDAO roomDAO;

    // Get all rooms
    // @GetMapping
    // public ResponseEntity<List<Room>> getAllRooms() {
    // List<Room> rooms = roomService.findAll();
    // return new ResponseEntity<>(rooms, HttpStatus.OK);
    // }

    @GetMapping
    public ResponseEntity<Page<Room>> getAllRooms(Pageable pageable) {
        Page<Room> rooms = roomService.findAll(pageable);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/checkroomNumber")
    public ResponseEntity<String> checkroomNumber(@RequestParam Integer roomNumber) {
        boolean isRoomnumberAvailable = roomDAO.existsByRoomNumber(roomNumber);
        return ResponseEntity.ok(Boolean.toString(isRoomnumberAvailable));
    }

    // Get room by ID
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.findbyId(roomId);
        if (room != null) {
            return new ResponseEntity<>(room, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new room
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@ModelAttribute RoomDTO roomDTO,
            @RequestPart(value = "roomImages", required = false) List<MultipartFile> roomImages) {
        try {
            Room createdRoom = roomService.create(roomDTO, roomImages);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ và trả về phản hồi phù hợp
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể tạo phòng. Vui lòng thử lại sau.");
        }
    }

    // Update a room
    @PutMapping("/{roomId}")
    public ResponseEntity<?> updateRoom(
        @PathVariable Long roomId,
        @ModelAttribute RoomDTO roomDTO,
        @RequestPart(value = "roomImages", required = false) List<MultipartFile> roomImages
    ) {
        try {
            Room updatedRoom = roomService.update(roomId, roomDTO, roomImages);
            return ResponseEntity.ok(updatedRoom);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ và trả về phản hồi phù hợp
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Không thể cập nhật phòng. Vui lòng thử lại sau.");
        }
    }
    
}
