package com.Apharma.sep4.DAO;

import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadingDAO implements Dao<Reading>
{
	@Autowired
	private SensorRepo sensorRepo;
	
	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private ReadingRepo readingRepo;
	
	public ReadingDAO()
	{
	}
	
	public void storeNewEntry(int hum, double temp, int co2, String timestamp, String roomId)
	{
		Room room = new Room();
		room.setId(roomId);
		
		//TODO: JPA add to reading without getting all children
		// Room room = roomRepo.getById(roomId);
		List<SensorDTO> sensors = sensorRepo.getRoomSensors(roomId);
		
		Reading humidity = new Reading(hum, timestamp);
		Reading temperature = new Reading(temp, timestamp);
		Reading co2Reading = new Reading(co2, timestamp);
		
		for (SensorDTO s: sensors)
		{
			System.out.println(s.getSensorType());
			
			Sensor sensor = new Sensor();
			sensor.setId(s.getId());
			sensor.setSensorType(s.getSensorType());
			sensor.setRoom(room);
			
			switch (sensor.getSensorType())
			{
				case Temperature:
				{
					temperature.setSensor(sensor);
				}
				break;
				
				case CO2:
				{
					sensor.setConstraintMinValue(s.getConstraintMinValue());
					sensor.setConstraintMaxValue(s.getConstraintMaxValue());
					co2Reading.setSensor(sensor);
				}
				break;
				
				case Humidity:
				{
					humidity.setSensor(sensor);
				}
				break;
			}
		}
		roomRepo.save(room);
	}
}
