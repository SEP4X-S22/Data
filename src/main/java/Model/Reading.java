package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reading
{
	private @Id @GeneratedValue int id;
	private int room;
	private SensorType sensor;
	private int value;
	private LocalDateTime timeStamp;

	public Reading()
	{
	}

	public Reading(int room, SensorType sensor, int value, LocalDateTime timeStamp)
	{
		this.room = room;
		this.sensor = sensor;
		this.value = value;
		this.timeStamp = timeStamp;
	}

	public Reading(int room, SensorType sensorType)
	{
		this.room = room;
		this.sensor = sensorType;
	}

	public int getId()
	{
		return id;
	}
	
	public int getRoom()
	{
		return room;
	}
	
	public String getSensorTypeString()
	{
		return sensor.name();
	}
	
	public int getValue()
	{
		return value;
	}
	
	public LocalDateTime getTimeStamp()
	{
		return timeStamp;
	}
	
	public enum SensorType
	{
		Humidity, CO2, Light, Temperature
	}
}
