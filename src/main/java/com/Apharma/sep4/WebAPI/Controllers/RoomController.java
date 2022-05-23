package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.DTO.RoomDTO;
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
  private final ReadingRepo readingRepo;
  private EntityManager entityManager;

  
  public RoomController(RoomRepo roomRepo, ReadingRepo readingRepo, EntityManager entityManager)
  {
    this.roomRepo = roomRepo;
    this.readingRepo = readingRepo;
    this.entityManager = entityManager;
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
  
  @GetMapping("/rooms/{roomId}/sensors/{sensorType}")
  private List<ReadingDTO> getSensorReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensorType)
  {
    return readingRepo.getReadingsForRoomIdAndSensorType(roomId, sensorType);
  }

  @GetMapping("rooms/{roomId}/current/{sensorType}")
  private ReadingDTO getCurrentReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensorType)
  {
    ReadingDTO current = readingRepo.getCurrentReading(roomId, sensorType);
    System.out.println(current);
    return current;
  }
}