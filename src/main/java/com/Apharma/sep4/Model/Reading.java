package com.Apharma.sep4.Model;

import javax.persistence.*;

/**
 Model class for a Sensor Reading. Used as an entity in JPA under the name of 'Readings'.
 
 @author 4X Data team
 @version 1.3 - 18.05.2022
 @implNote Changed 'readingValue' field data type from int to double in order to store a single decimal point
 precision value for the temperature. - Aldís Eir Hansen
 */
@Entity
@Table(name = "Readings")
public class Reading
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double readingValue;
	private String timeStamp;
	@ManyToOne
	@JoinColumn(name = "sensor_id")
	private Sensor sensor;
	
	/**
	 Required empty constructor for JPA.
	 */
	public Reading()
	{
	}
	
	/**
	 Two argument constructor for a Reading object.
	 
	 @param readingValue Double value of the Reading object
	 @param timeStamp String representation of the time when the hardware recorded the data
	 */
	public Reading(double readingValue, String timeStamp)
	{
		this.readingValue = readingValue;
		this.timeStamp = timeStamp;
	}
	
	/**
	 Three argument constructor for a Reading object.
	 
	 @param readingValue Double value of the Reading object
	 @param timeStamp String representation of the time when the hardware recorded the data
	 @param sensor Sensor object
	 */
	public Reading(double readingValue, String timeStamp, Sensor sensor)
	{
		this.readingValue = readingValue;
		this.timeStamp = timeStamp;
		this.sensor = sensor;
	}
	
	public void setSensor(Sensor sensor)
	{
		this.sensor = sensor;
		sensor.getReadings().add(this);
	}
	
	public Sensor getSensor()
	{
		return sensor;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public double getReadingValue()
	{
		return readingValue;
	}
	
	public void setReadingValue(double readingValue)
	{
		this.readingValue = readingValue;
	}
	
	public String getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	@Override public boolean equals(Object obj)
	{
    if (this == obj)
    {
      return true;
    }
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }
		Reading reading = (Reading) obj;
		return id == reading.id && readingValue == reading.readingValue && timeStamp.equals(reading.timeStamp);
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "Reading {" + "id = " + id + ", readingValue = " + readingValue + ", timeStamp = " + timeStamp + '}';
	}
}
