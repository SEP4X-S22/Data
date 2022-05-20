package com.Apharma.sep4.DTO;

import com.Apharma.sep4.Model.Sensor;

public class SensorDTO
{
	private int id;
	private Sensor.SensorType sensorType;
	private double constraintMinValue;
	private double constraintMaxValue;
	private double readingValue;
	
	public SensorDTO(int id, Sensor.SensorType sensorType, double constraintMinValue, double constraintMaxValue,
					 double readingValue)
	{
		this.id = id;
		this.sensorType = sensorType;
		this.constraintMinValue = constraintMinValue;
		this.constraintMaxValue = constraintMaxValue;
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
	
	public double getConstraintMinValue()
	{
		return constraintMinValue;
	}
	
	public void setConstraintMinValue(double constraintMinValue)
	{
		this.constraintMinValue = constraintMinValue;
	}
	
	public double getConstraintMaxValue()
	{
		return constraintMaxValue;
	}
	
	public void setConstraintMaxValue(double constraintMaxValue)
	{
		this.constraintMaxValue = constraintMaxValue;
	}
	
	@Override public String toString()
	{
		return "SensorDTO {" + "id = " + id + ", sensorType = " + sensorType + ", readingValue = " + readingValue + '}';
	}
}
