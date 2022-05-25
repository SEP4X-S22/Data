package com.Apharma.sep4.Persistence.DTO;

import com.Apharma.sep4.Model.Sensor;

/**
 DTO class made to hold relevant variables of Sensor model class objects.
 
 @author 4X Data team
 @version 2.0 - 20.05.2022
 @implNote Added fields for latest reading value as well as the upper and lower threshold constraints. - 4X Data team
 */
public class SensorDTO
{
	private int id;
	private Sensor.SensorType sensorType;
	private int constraintMin;
	private int constraintMax;
	private double readingValue;
	
	/**
	 Five argument constructor for a SensorDTO object.
	 
	 @param id String ID
	 @param sensorType Enum of type of Sensor
	 @param min Integer of lower threshold constraint value
	 @param max Integer of upper threshold constraint value
	 @param readingValue Double of the most recently recorded Reading value
	 */
	public SensorDTO(int id, Sensor.SensorType sensorType, int min, int max, double readingValue)
	{
		this.id = id;
		this.sensorType = sensorType;
		this.constraintMin = min;
		this.constraintMax = max;
		this.readingValue = readingValue;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Sensor.SensorType getSensorType()
	{
		return sensorType;
	}
	
	public void setSensorType(Sensor.SensorType sensorType)
	{
		this.sensorType = sensorType;
	}
	
	public double getReadingValue()
	{
		return readingValue;
	}
	
	public void setReadingValue(double readingValue)
	{
		this.readingValue = readingValue;
	}
	
	public int getConstraintMinValue()
	{
		return constraintMin;
	}
	
	public void setConstraintMinValue(int constraintMinValue)
	{
		this.constraintMin = constraintMinValue;
	}
	
	public int getConstraintMaxValue()
	{
		return constraintMax;
	}
	
	public void setConstraintMaxValue(int constraintMaxValue)
	{
		this.constraintMax = constraintMaxValue;
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
  @Override public String toString()
  {
    return "SensorDTO {" + "ID = " + id + ", sensorType = " + sensorType + ", readingValue = " + readingValue + '}';
  }
}
