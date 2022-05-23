package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.DTO.SensorConstraintsDTO;
import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.MiddlePoint.MiddlePointDecoder;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class SensorController
{
	private final SensorRepo sensorRepo;
	private EntityManager entityManager;
	@Autowired
	private MiddlePointDecoder middlePointDecoder;
	
	public SensorController(SensorRepo sensorRepo, EntityManager entityManager)
	{
		this.sensorRepo = sensorRepo;
		this.entityManager = entityManager;
	}
	
	@GetMapping("/rooms/{roomId}/sensors")
	private List<SensorDTO> getRoomSensors(@PathVariable String roomId)
	{
		return sensorRepo.getRoomSensors(roomId);
	}
	
	@GetMapping("sensor/{sensorId}")
	private SensorConstraintsDTO getSensorConstraints(@PathVariable int sensorId)
	{
		return sensorRepo.getSensorConstraints(sensorId);
	}
	
	@PatchMapping("sensor/constraints")
	private void setSensorConstraints(@RequestParam int id, @RequestParam double min, @RequestParam double max)
	{
		middlePointDecoder.createTelegram();
		sensorRepo.setSensorConstraints(id, min, max);
	}
}
