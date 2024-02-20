package com.ps20652.Hotel.DTO;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

import com.ps20652.Hotel.entity.Room;
import com.ps20652.Hotel.entity.RoomType;

import lombok.Data;

@Data
public class RoomDTO {
    // private Room newRoom;
    private List<MultipartFile> roomImages;
    private Integer roomNumber;
    private Long roomType;
    private String status;
    private BigDecimal price;

    
}