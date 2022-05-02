package com.Apharma.sep4.Model;

import javax.persistence.*;

@Entity
@Table(name = "ROOMS")
public class Room
{
	private @Id @GeneratedValue int roomId;
	private @Embedded Sensor[] sensors;
	
	public Room()
	{
	
	}
	
	public Room(int id, Sensor[] sensors)
	{
		roomId = id;
		this.sensors = sensors;
	}
	
	public int getRoomId()
	{
		return roomId;
	}
	
	public Sensor[] getAllSensors()
	{
		return sensors;
	}
	
	public Sensor getSensor(int sensorId)
	{
		return sensors[sensorId+1];
	}
	
	public void setRoomId(int id)
	{
		if (id >= 0)
		{
			roomId = id;
		}
	}
	
	public void setSensors(Sensor[] sensors)
	{
		this.sensors = sensors;
	}
	
	@Override public String toString()
	{
		StringBuilder s = new StringBuilder("{" + "roomId = " + roomId + "\nsensors = ");
		for (Sensor sensor:sensors)
		{
			s.append(sensor.toString());
		}
		
		s.append("}");
		
		return s.toString();
	}
}
