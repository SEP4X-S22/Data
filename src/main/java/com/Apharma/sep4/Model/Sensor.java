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

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Sensor
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private SensorType sensor;


  @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
  private List<Reading> readings = new ArrayList<>();

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "room_id")
  private Room room;

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Sensor sensor1 = (Sensor) o;
    return id == sensor1.id && sensor == sensor1.sensor && readings.equals(
        sensor1.readings) && room.equals(sensor1.room);
  }

  @Override public int hashCode()
  {
    return Objects.hash(id, sensor, readings, room);
  }

  public void addReading(Reading reading){
    readings.add(reading);
    reading.setSensor(this);
  }

  public void removeReading(Reading reading){
    readings.remove(reading);
    reading.setSensor(null);
  }

  public Room getRoom()
  {
    return room;
  }

  @Override public String toString()
  {
    return "Sensor{" + "id=" + id + ", sensor=" + sensor + ", readings="
        + readings + '}';
  }

  public void setRoom(Room room)
  {
    this.room = room;
  }

  public Sensor(SensorType sensor, List<Reading> readings, Room room)
  {
    this.readings = readings;
    this.sensor = sensor;
    this.room = room;
  }

  public Sensor(){

  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public SensorType getSensor()
  {
    return sensor;
  }

  public void setSensor(SensorType sensor)
  {
    this.sensor = sensor;
  }

  public List<Reading> getReadings()
  {
    return readings;
  }

  public void setReadings(List<Reading> readings)
  {
    this.readings = readings;
  }

  public enum SensorType
  {
    Humidity, CO2, Light, Temperature
  }

}
