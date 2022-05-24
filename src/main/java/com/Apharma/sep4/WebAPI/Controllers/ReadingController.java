package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReadingController
{
	private final ReadingRepo readingRepo;
	
	public ReadingController(ReadingRepo readingRepo)
	{
		this.readingRepo = readingRepo;
	}
	
	@GetMapping("/rooms/{roomId}/sensors/{sensorType}")
	private List<ReadingDTO> getSensorReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensorType)
	{
		return readingRepo.getReadingsForRoomIdAndSensorType(roomId, sensorType);
	}
	
	@GetMapping("/rooms/{roomId}/current/{sensorType}")
	private ReadingDTO getCurrentReading(@PathVariable String roomId, @PathVariable Sensor.SensorType sensorType)
	{
		ReadingDTO current = readingRepo.getCurrentReading(roomId, sensorType);
		System.out.println(current);
		return current;
	}
}
