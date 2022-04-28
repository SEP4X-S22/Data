package Model;

import java.time.LocalDateTime;

public class Reading
{
	private int id;
	private int room;
	private SensorType sensor;
	private int value;
	private LocalDateTime timeStamp;
	
	public Reading(int room, SensorType sensor, int value, LocalDateTime timeStamp)
	{
		this.room = room;
		this.sensor = sensor;
		this.value = value;
		this.timeStamp = timeStamp;
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
	
	protected enum SensorType
	{
		Humidity, CO2, Light, Temperature
	}
}
