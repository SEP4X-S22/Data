package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import com.Apharma.sep4.Persistence.DTO.RoomDTO;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class RoomController
{
  private final RoomRepo roomRepo;
  
  public RoomController(RoomRepo roomRepo)
  {
    this.roomRepo = roomRepo;
  }
  
  @GetMapping("/")
  private String getAll()
  {
    return "Hello from Data Team!";
  }
  
  @GetMapping("/rooms")
  private List<RoomDTO> getAllRooms()
  {
    return roomRepo.getAllRooms();
  }
}