package com.Apharma.sep4.Model;
//
//<<<<<<< Updated upstream
//import javax.persistence.*;
//
//@Entity
//@Table(name = "SENSORS")
//public class Sensor
//{
//	private @Id @GeneratedValue int sensorId;
//	private SensorType sensorType;
//	private @Embedded Reading[] readings;
//	private int maxValue;
//	private int minValue;
//
//	public Sensor()
//	{
//
//	}
//
//	public Sensor(int id, SensorType type, Reading[] readings, int max, int min)
//	{
//		sensorId = id;
//		sensorType = type;
//		this.readings = readings;
//		maxValue = max;
//		minValue = min;
//	}
//
//	public int getSensorId()
//	{
//		return sensorId;
//	}
//
//	public Reading[] getReadings()
//	{
//		return readings;
//	}
//
//	public SensorType getSensorType()
//	{
//		return sensorType;
//	}
//
//	public int getMaxValue()
//	{
//		return maxValue;
//	}
//
//	public int getMinValue()
//	{
//		return minValue;
//	}
//
//	public void setSensorId(int sensorId)
//	{
//		this.sensorId = sensorId;
//	}
//
//	public void setReadings(Reading[] readings)
//	{
//		this.readings = readings;
//	}
//
//	public void setSensorType(SensorType sensorType)
//	{
//		this.sensorType = sensorType;
//	}
//
//	public void setMaxValue(int maxValue)
//	{
//		this.maxValue = maxValue;
//	}
//
//	public void setMinValue(int minValue)
//	{
//		this.minValue = minValue;
//	}
//
//	@Override public String toString()
//	{
//		StringBuilder s = new StringBuilder("{sensorId = "+ sensorId +", sensorType = " + sensorType.toString());
//
//		if (readings.length >= 1)
//		{
//			for (Reading r:readings)
//			{
//				s.append(r.toString() + "\n");
//			}
//		}
//
//		s.append("}");
//
//		return s.toString();
//	}
//
//	public enum SensorType
//	{
//		Humidity, CO2, Light, Temperature
//	}

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Sensor
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private SensorType sensorType;
  @OneToMany(targetEntity = Reading.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name= "sensor_reading_fk", referencedColumnName = "id")
  private List<Reading> readings = new ArrayList<>();
  

  public Sensor(){
  }
  
  public Sensor(SensorType sensorType, List<Reading> readings, Room room)
  {
    this.readings = readings;
    this.sensorType = sensorType;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public SensorType getSensorType()
  {
    return sensorType;
  }

  public void setSensorType(SensorType sensorType)
  {
    this.sensorType = sensorType;
  }

  public List<Reading> getReadings()
  {
    return readings;
  }

  public void setReadings(List<Reading> readings)
  {
    this.readings = readings;
  }

  @Override public int hashCode()
  {
    return Objects.hash(id, sensorType, readings);
  }

  @Override public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Sensor sensor1 = (Sensor) obj;
    return id == sensor1.id && sensorType == sensor1.sensorType && readings.equals(
        sensor1.readings);
  }

  @Override public String toString()
  {
    return "Sensor{" + "id=" + id + ", sensor=" + sensorType + ", readings=" + readings + '}';
  }
  
  public enum SensorType
  {
    Humidity, CO2, Light, Temperature
  }
}
