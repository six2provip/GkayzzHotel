package com.ps20652.Hotel.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.dao.TypeRoomDAO;
import com.ps20652.Hotel.entity.RoomType;
import com.ps20652.Hotel.services.RoomTypeService;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private TypeRoomDAO typeroomDAO;

    @Override
    public List<RoomType> findAll() {
       return typeroomDAO.findAll();
    }

    @Override
    public RoomType findbyId(Long id) {
        return typeroomDAO.findById(id).get();
    }
    
}
