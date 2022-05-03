package com.Apharma.sep4.Model;

<<<<<<< Updated upstream
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
=======
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Sensor> sensors = new ArrayList<>();

  @Override public String toString()
  {
    return "Room{" + "id=" + id + ", sensors=" + sensors + '}';
  }

  public Room()
  {
  }
  public void addSensor(Sensor sensor){
    sensors.add(sensor);
    sensor.setRoom(this);
  }

  public void removeSensor(Sensor sensor){
    sensors.remove(sensor);
    sensor.setRoom(null);
  }

  public Room(List<Sensor> sensors){
    sensors = sensors;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public List<Sensor> getSensorsList(){
    return sensors;
  }

  public void setSensorsList(ArrayList<Sensor> sensorsList)
  {
    this.sensors = sensorsList;
  }
>>>>>>> Stashed changes
}
