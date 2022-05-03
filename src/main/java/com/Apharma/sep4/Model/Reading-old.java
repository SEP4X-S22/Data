//package com.Apharma.sep4.Model;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Table(name = "SENSOR_READING")
//public class Reading
//{
//	private @Id @GeneratedValue int id;
//	private int room;
//	private SensorType sensor;
//	private int readingValue; //"value" is restricted in database, so I changed it
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date timeStamp; //changed to Date because it can help us filter readings by date/time/timestamp much easier
//
//	public Reading()
//	{
//	}
//
//	public Reading(int room, SensorType sensor, int readingValue, Date timeStamp)
//	{
//		this.room = room;
//		this.sensor = sensor;
//		this.readingValue = readingValue;
//		this.timeStamp = timeStamp;
//	}
//
//	public Reading(int room, SensorType sensorType)
//	{
//		this.room = room;
//		this.sensor = sensorType;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setRoom(int room)
//	{
//		this.room = room;
//	}
//
//	public int getRoom()
//	{
//		return room;
//	}
//
//	public void setSensor(SensorType sensor)
//	{
//		this.sensor = sensor;
//	}
//
//	public SensorType getSensorType()
//	{
//		return sensor;
//	}
//
//	public void setReadingValue(int readingValue)
//	{
//		this.readingValue = readingValue;
//	}
//
//	public int getReadingValue()
//	{
//		return readingValue;
//	}
//
//	public void setTimeStamp(Date timeStamp)
//	{
//		this.timeStamp = timeStamp;
//	}
//
//	public Date getTimeStamp()
//	{
//		return timeStamp;
//	}
//
//	public enum SensorType
//	{
//		Humidity, CO2, Light, Temperature
//	}
//
//	@Override public String toString()
//	{
//		return "Reading{" + "id=" + id + ", room=" + room + ", sensor=" + sensor
//				+ ", readingValue=" + readingValue + ", timeStamp=" + timeStamp + '}';
//	}
//}
