package com.Apharma.sep4.Controllers.WebAPI;

import com.Apharma.sep4.RawData.MiddlePointDecoder;
import com.Apharma.sep4.Persistence.DTO.SensorDTO;
import com.Apharma.sep4.Persistence.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 Controller class for handling requests to the API that deal with Sensors.
 
 @author 4X Data team
 @version 2.1 - 24.05.2022
 @implNote Added logger endpoints. - Claudiu Cordunianu
 */
@RestController
public class SensorController
{
	private final SensorRepo sensorRepo;
	@Autowired
	private MiddlePointDecoder middlePointDecoder;
	
	/**
	 One argument constructor for a SensorController.
	 
	 @param sensorRepo SensorRepo object to access the database
	 */
	public SensorController(SensorRepo sensorRepo)
	{
		this.sensorRepo = sensorRepo;
	}
	
	/**
	 Method exposing the endpoint for getting the logger from the MiddlePointDecoder class.
	 
	 @return String of decoder data log
	 */
	@GetMapping("/log")
	private String getLog()
	{
		return middlePointDecoder.getLog();
	}
	
	/**
	 Method exposing the endpoint for clearing the logger in the MiddlePointDecoder class.
	 */
	@GetMapping("/clearLog")
	private void clearLog()
	{
		middlePointDecoder.clearLog();
	}
	
	/**
	 Method exposing the endpoint for getting Sensors that are in a specific Room.
	 
	 @param roomId String of the Room ID
	 @return String of a List of Sensors represented by SensorDTO objects from the SensorRepo
	 */
	@GetMapping("/sensors/{roomId}")
	private List<SensorDTO> getRoomSensors(@PathVariable String roomId)
	{
		return sensorRepo.getRoomSensors(roomId);
	}
	
	/**
	 Method exposing the endpoint for setting the minimum and maximum threshold constraints for a specific Sensor.
	 
	 @param id Integer of the Sensor ID
	 @param min Integer of the new minimum threshold constraint
	 @param max Integer of the new maximum threshold constraint
	 */
	@PatchMapping("/sensors/constraints")
	private void setSensorConstraints(@RequestParam int id, @RequestParam int min, @RequestParam int max)
	{
		middlePointDecoder.createTelegram(id, min, max);
		sensorRepo.setSensorConstraints(id, min, max);
	}
}
