package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import com.Apharma.sep4.Persistence.Repos.ReadingRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 Controller class for handling requests to the API that deal with Readings.
 Placeholder to see if Android needs this method in the end.
 
 @author 4X Data team
 @version 2.1 - 26.05.2022
 @implNote Removed unused endpoint. - Aldís Eir Hansen & Claudiu Cordunianu
 */
@RestController
public class ReadingController
{
	private final ReadingRepo readingRepo;
	
	/**
	 One argument constructor for a ReadingController.
	 
	 @param readingRepo ReadingRepo object to access the database
	 */
	public ReadingController(ReadingRepo readingRepo)
	{
		this.readingRepo = readingRepo;
	}
	
	/**
	 Method exposing the endpoint for getting ReadingDTO objects from a certain type of Sensor in a Room.
	 
	 @param roomId String of a Room ID
	 @param sensorType Enum of SensorType
	 @return List of ReadingDTO objects from the ReadingRepo
	 */
	@GetMapping("/readings/{roomId}/{sensorType}")
	private List<ReadingDTO> getSensorReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensorType)
	{
		return readingRepo.getReadingsForRoomIdAndSensorType(roomId, sensorType);
	}
}
