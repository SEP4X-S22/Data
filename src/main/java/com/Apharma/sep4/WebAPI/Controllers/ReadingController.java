package com.Apharma.sep4.WebAPI.Controllers;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class ReadingController
{
	private final RoomRepo roomRepo;

	public ReadingController(RoomRepo roomRepo)
	{
		this.roomRepo = roomRepo;
	}

	@GetMapping("/")
	private String getAll()
	{
		return "Hello from Data Team!";
	}

	@GetMapping("/{room}")
	private List<Room> getAll(@PathVariable int room)
	{
		return roomRepo.findByRoomId(room);
	}

	@GetMapping("/{room}/{sensorType}")
	private List<Room> getSensorReading(@PathVariable int room, @PathVariable Sensor.SensorType sensorType)
	{
		return roomRepo.findByRoomAndSensor(room,sensorType);
	}

	@GetMapping("/{room}/{sensorType}/{time}")
	private List<Room> getSensorReading(@PathVariable int room, @PathVariable Sensor.SensorType sensorType, @PathVariable String time)
	{

		try
		{
			return roomRepo.findByRoomAndSensorAndTimeStampBefore(
					room, sensorType, new SimpleDateFormat("dd-MM" + "-yyyy_HH:mm").parse(time));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
