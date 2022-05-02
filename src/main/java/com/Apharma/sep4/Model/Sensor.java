package com.Apharma.sep4.Model;

import javax.persistence.*;

@Entity
@Table(name = "SENSORS")
public class Sensor
{
	private @Id @GeneratedValue int sensorId;
	private SensorType sensorType;
	private @Embedded Reading[] readings;
	private int maxValue;
	private int minValue;
	
	public Sensor()
	{
	
	}
	
	public Sensor(int id, SensorType type, Reading[] readings, int max, int min)
	{
		sensorId = id;
		sensorType = type;
		this.readings = readings;
		maxValue = max;
		minValue = min;
	}
	
	public int getSensorId()
	{
		return sensorId;
	}
	
	public Reading[] getReadings()
	{
		return readings;
	}
	
	public SensorType getSensorType()
	{
		return sensorType;
	}
	
	public int getMaxValue()
	{
		return maxValue;
	}
	
	public int getMinValue()
	{
		return minValue;
	}
	
	public void setSensorId(int sensorId)
	{
		this.sensorId = sensorId;
	}
	
	public void setReadings(Reading[] readings)
	{
		this.readings = readings;
	}
	
	public void setSensorType(SensorType sensorType)
	{
		this.sensorType = sensorType;
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
	}
	
	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
	}
	
	@Override public String toString()
	{
		StringBuilder s = new StringBuilder("{sensorId = "+ sensorId +", sensorType = " + sensorType.toString());
		
		if (readings.length >= 1)
		{
			for (Reading r:readings)
			{
				s.append(r.toString() + "\n");
			}
		}
		
		s.append("}");
		
		return s.toString();
	}
	
	public enum SensorType
	{
		Humidity, CO2, Light, Temperature
	}
}
