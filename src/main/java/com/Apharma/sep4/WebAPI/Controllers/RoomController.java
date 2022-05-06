package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.DTO.RoomDTO;
import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class RoomController
{
  private final RoomRepo roomRepo;
  private final SensorRepo sensorRepo;
  private final ReadingRepo readingRepo;
  private EntityManager entityManager;

  public RoomController(RoomRepo roomRepo, SensorRepo sensorRepo,
      ReadingRepo readingRepo, EntityManager entityManager)
  {
    this.roomRepo = roomRepo;
    this.sensorRepo = sensorRepo;
    this.readingRepo = readingRepo;
    this.entityManager = entityManager;
  }
  
  @GetMapping("/")
  private String getAll()
  {
    return "Hello from Data Team!";
  }
  @GetMapping("/rooms")
  private List<Room> getAllRooms()
  {
    return roomRepo.findAll();
  }

  @GetMapping("/roomsDTO")
  private List<RoomDTO> getAllRoomsDTO()
  {
    return roomRepo.getAllRooms();
  }
  
  @GetMapping("/rooms/{roomId}/sensors")
  private List<SensorDTO> getRoomSensors(@PathVariable String roomId)
  {
    return sensorRepo.getRoomSensors(roomId);
  }
  
  @GetMapping("/rooms/{roomId}/sensors/{sensor}")
  private List<ReadingDTO> getSensorReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensor)
  {
    return readingRepo.getReadingsForRoomIdAndSensorType(roomId, sensor);
  }
}
