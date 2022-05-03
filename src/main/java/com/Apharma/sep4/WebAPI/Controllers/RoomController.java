package com.Apharma.sep4.WebAPI.Controllers;

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

  @GetMapping("/") private String getAll()
  {
    return "Hello from Data Team!";
  }

  @GetMapping("/rooms") private List<Room> getAllRooms()
  {
    return roomRepo.findAll();
  }

  @GetMapping("/rooms/{roomId}") private Room getRoomById(@PathVariable int roomId)
  {
    return roomRepo.findById(roomId);
  }

  @GetMapping("/room/sensors/{roomId}") private List<Sensor> getRoomSensors(@PathVariable int roomId)
  {
    return roomRepo.findById(roomId).getSensorsList();

  }

  @GetMapping("/room/sensor/{roomId}/{sensor}") private List<Reading> getSensorReading(@PathVariable int roomId, @PathVariable int sensor)
  {
    return roomRepo.findById(roomId).getSensorsList().get(sensor).getReadings();
  }
}
