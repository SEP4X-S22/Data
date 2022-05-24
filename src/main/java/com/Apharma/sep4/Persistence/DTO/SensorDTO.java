package com.Apharma.sep4.Persistence.DTO;

import com.Apharma.sep4.Model.Sensor;

public class SensorDTO
{
	private int id;
	private Sensor.SensorType sensorType;
	private int constraintMin;
	private int constraintMax;
	private double readingValue;
	
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
  
  @Override public String toString()
  {
    return "SensorDTO {" + "id = " + id + ", sensorType = " + sensorType + ", readingValue = " + readingValue + '}';
  }
}
