package com.Apharma.sep4.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 Model class for a Sensor object. Used as an entity in JPA under the name of 'Sensors'.
 
 @author 4X Data team, Stefan Georgiev
 @version 5 - 23.05.2022
 @implNote Changed constraintMinValue and constraintMaxValue fields' data types from double to int.
 */
@Entity
@Table(name = "Sensors")
public class Sensor
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private SensorType sensorType;
	private int constraintMinValue;
	private int constraintMaxValue;
	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reading> readings;
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	
	/**
	 Required empty constructor for JPA initializing the 'readings' field to a new empty ArrayList.
	 */
	public Sensor()
	{
		readings = new ArrayList<>();
	}
	
	public List<Reading> getReadings()
	{
		return readings;
	}
	
	public void setReadings(List<Reading> readings)
	{
		this.readings = readings;
	}
	
	public Room getRoom()
	{
		return room;
	}
	
	/**
	 Setter for the 'room' field, also gets the Room objects field containing its Sensor objects and adds this Sensor
	 object to that variable.
	 */
	public void setRoom(Room room)
	{
		this.room = room;
		room.getSensors().add(this);
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
	
	public int getConstraintMinValue()
	{
		return constraintMinValue;
	}
	
	public void setConstraintMinValue(int constraintMinValue)
	{
		this.constraintMinValue = constraintMinValue;
	}
	
	public int getConstraintMaxValue()
	{
		return constraintMaxValue;
	}
	
	public void setConstraintMaxValue(int constraintMaxValue)
	{
		this.constraintMaxValue = constraintMaxValue;
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
		Sensor sensor1 = (Sensor) obj;
		return id == sensor1.id && sensorType == sensor1.sensorType;
	}
	
	@Override public int hashCode()
	{
		return Objects.hash(id, sensorType);
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "Sensor {" + "ID = " + id + ", sensorType = " + sensorType + ", readings = " + '}';
	}
	
	/**
	 An enum declaring the four Sensor types; Humidity, CO2, Light and Temperature.
	 */
	public enum SensorType
	{
		Humidity, CO2, Light, Temperature
	}
}
