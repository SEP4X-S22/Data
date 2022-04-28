package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ReadingController
{
	private final ReadingRepo readingRepo;

	public ReadingController(ReadingRepo readingRepo)
	{
		this.readingRepo = readingRepo;
	}

	@GetMapping("/room")
	private List<Reading> getAll()
	{
		return readingRepo.findAll();
	}

	@GetMapping("/{room}/{sensorType}")
	private List<Reading> getSensorReading(@PathVariable int room, @PathVariable Reading.SensorType sensorType)
	{
		return readingRepo.findByRoomAndSensor(room,sensorType);
	}

	@GetMapping("/{room}/{sensorType}/{time}")
	private List<Reading> getSensorReading(@PathVariable int room, @PathVariable Reading.SensorType sensorType, @PathVariable String time)
	{

		try
		{
			return readingRepo.findByRoomAndSensorAndTimeStampBefore(room,sensorType,new SimpleDateFormat("dd-MM-yyyy_HH:mm").parse(time));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
