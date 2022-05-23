package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.DTO.RoomDTO;
import com.Apharma.sep4.DTO.SensorConstraintsDTO;
import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.MiddlePoint.MiddlePointDecoder;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private MiddlePointDecoder middlePointDecoder;

  public RoomController(RoomRepo roomRepo, SensorRepo sensorRepo, ReadingRepo readingRepo, EntityManager entityManager)
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
  private List<RoomDTO> getAllRooms()
  {
    return roomRepo.getAllRooms();
  }
  
  @GetMapping("/rooms/{roomId}/sensors")
  private List<SensorDTO> getRoomSensors(@PathVariable String roomId)
  {
    return sensorRepo.getRoomSensors(roomId);
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

  @GetMapping("sensor/{sensorId}")
  private SensorConstraintsDTO getSensorConstraints(@PathVariable int sensorId)
  {
    SensorConstraintsDTO sensorConstraints = sensorRepo.getSensorConstraints(sensorId);
    return sensorConstraints;
  }

  @PatchMapping("sensor/constraints")
  private void setSensorConstraints(@RequestParam int id, @RequestParam double min, @RequestParam double max)
  {
    middlePointDecoder.createTelegram();
   sensorRepo.setSensorConstraints(id, min, max);
  }
}
