package com.ps20652.Hotel.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ps20652.Hotel.DTO.RoomDTO;
import com.ps20652.Hotel.dao.RoomDAO;
import com.ps20652.Hotel.entity.Room;
import com.ps20652.Hotel.entity.RoomImage;
import com.ps20652.Hotel.entity.RoomType;
import com.ps20652.Hotel.services.RoomService;
import com.ps20652.Hotel.services.RoomTypeService;

@Service
public class RoomServerImpl implements RoomService {

    @Value("${images.directory}")
    private String imagesDirectory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private RoomTypeService roomTypeService;

    @Override
    public Room findbyId(Long id) {

        return roomDAO.findById(id).get();
    }

    // @Override
    // public List<Room> findAll() {

    //     return roomDAO.findAll();
    // }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomDAO.findAll(pageable);
    }

    @Override
    public Room create(RoomDTO roomDTO,  List<MultipartFile> roomImages) {

        boolean existsByRoomNumber = roomDAO.existsByRoomNumber(roomDTO.getRoomNumber());
        Room newRoom = new Room();
        if(!existsByRoomNumber){

            try {
           
                
                // Kiểm tra RoomDTO có hợp lệ không
               
        
                newRoom.setPrice(roomDTO.getPrice());
                newRoom.setRoomNumber(roomDTO.getRoomNumber());
                RoomType type = roomTypeService.findbyId(roomDTO.getRoomType());
                newRoom.setRoomType(type);
                newRoom.setStatus("Còn phòng");
                
                // Tạo phòng mới
                Room createdRoom = roomDAO.save(newRoom);
        
                // Kiểm tra và lưu trữ hình ảnh
                if (roomImages != null && !roomImages.isEmpty()) {
                    List<RoomImage> roomImageList = new ArrayList<>();
    
                    for (MultipartFile image : roomImages) {
                        String imageUrl = saveImage(image);
    
                        // Tạo đối tượng RoomImage
                        RoomImage roomImage = new RoomImage();
                        roomImage.setRoom(createdRoom);
                        roomImage.setImageUrl(imageUrl);
    
                        // Thêm vào danh sách hình ảnh của phòng
                        roomImageList.add(roomImage);
                    }
    
                    // Thêm danh sách hình ảnh vào phòng và lưu lại phòng để cập nhật danh sách hình ảnh
                    createdRoom.getRoomImages().addAll(roomImageList);
                    createdRoom = roomDAO.save(createdRoom);
                }
    
                return createdRoom;
            } catch (Exception e) {
                // Xử lý ngoại lệ hoặc ném lại nó nếu cần thiết
                e.printStackTrace();
                throw new RuntimeException("Không thể tạo phòng", e);
            }

        }

        roomDAO.save(newRoom);
        
        return newRoom;
    }
    

    private String saveImage(MultipartFile image) {
        String imageString = "Logo.png";
        try {
            if (!image.isEmpty()) {
                // Đảm bảo thư mục tồn tại
                File directory = new File(imagesDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
    
                // Đường dẫn lưu trữ hình ảnh
                Path path = Paths.get("src/main/resources/static/assets/images/");
    
                // Tạo tên mới cho hình ảnh để tránh trùng lặp
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                String fileExtension = StringUtils.getFilenameExtension(fileName);
                String newFileName = System.currentTimeMillis() + "." + fileExtension;
    
                // Lưu trữ hình ảnh vào thư mục uploads với tên mới
                Files.copy(image.getInputStream(), path.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
    
                // Lấy tên hình ảnh đã lưu
                imageString = newFileName;
            }
    
            // Trả về đường dẫn của hình ảnh đã lưu
            return imageString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    

    @Override
    public Room update(Room room) {
        return roomDAO.save(room);
    }

}
