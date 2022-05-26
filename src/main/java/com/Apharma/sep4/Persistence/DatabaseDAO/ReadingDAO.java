package com.Apharma.sep4.Persistence.DatabaseDAO;

import com.Apharma.sep4.Persistence.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.Persistence.Repos.RoomRepo;
import com.Apharma.sep4.Persistence.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 Implementation class of iReadingDAO.
 
 @author 4X Data team
 @version 2.1 - 25.05.2022
 @implNote Added light sensor reading. - Claudiu Cordunianu
 */

public class ReadingDAO implements iReadingDAO
{
	@Autowired
	private SensorRepo sensorRepo;
	
	@Autowired
	private RoomRepo roomRepo;
	
	/**
	 Required empty constructor for JPA.
	 */
	public ReadingDAO()
	{
	
	}
	
	/**
	 Implementation of interface method to add a new row to the 'Readings' table in the database.
	 Firstly, it creates a Room object as it is the outermost layer of the model class "cocoon". Its ID is set to the
	 argument value and a List of SensorDTO objects is fetched from the SensorRepo by the ID of the Room. New Reading
	 objects are created to hold the argument data. Secondly, for each SensorDTO in the list it recreates and configures
	 a Sensor object from the database to house the Reading. Thirdly, there is a switch statement that takes
	 the SensorType enum  of the newly reconstructed Sensor. Inside the statement the Reading objects' Sensor field is
	 set to the switch argument Sensor. Thus, the created Reading is stored in the Sensor who has the right SensorType
	 and belongs in the Room whose ID was passed. Ultimately, the RoomRepo is called to update the Room entity with the
	 Sensor objects containing the new Reading objects.
	 
	 @param hum Integer value of reading from the Humidity sensor
	 @param temp Integer value of reading from the Temperature sensor
	 @param co2 Integer value of reading from the CO2 sensor
	 @param light Integer value of reading from the Light sensor
	 @param timestamp String representing the timestamp of when the data was collected
	 @param roomId String ID of the Room object the Sensor whose Reading this is
	 */
	public void storeNewEntry(int hum, double temp, int co2, int light, String timestamp, String roomId)
	{
		Room room = new Room();
		room.setId(roomId);
		
		List<SensorDTO> sensors = sensorRepo.getRoomSensors(roomId);
		
		Reading humidity = new Reading(hum, timestamp);
		Reading temperature = new Reading(temp, timestamp);
		Reading co2Reading = new Reading(co2, timestamp);
		Reading lightReading = new Reading(light, timestamp);
		
		for (SensorDTO s: sensors)
		{
			Sensor sensor = new Sensor();
			sensor.setId(s.getId());
			sensor.setSensorType(s.getSensorType());
			sensor.setRoom(room);
			sensor.setConstraintMinValue(s.getConstraintMinValue());
			sensor.setConstraintMaxValue(s.getConstraintMaxValue());

			switch (sensor.getSensorType())
			{
				case Temperature:
				{
					temperature.setSensor(sensor);
				}
				break;

				case CO2:
				{
					co2Reading.setSensor(sensor);
				}
				break;
				
				case Humidity:
				{
					humidity.setSensor(sensor);
				}
				break;

				case Light:
				{
					lightReading.setSensor(sensor);
				}
			}
		}
		roomRepo.save(room);
	}
}
