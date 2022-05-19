package com.Apharma.sep4.Model;
//
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "ROOMS")
//public class Room
//{
//	private @Id @GeneratedValue int roomId;
//	private @Embedded Sensor[] sensors;
//
//	public Room()
//	{
//
//	}
//
//	public Room(int id, Sensor[] sensors)
//	{
//		roomId = id;
//		this.sensors = sensors;
//	}
//
//	public int getRoomId()
//	{
//		return roomId;
//	}
//
//	public Sensor[] getAllSensors()
//	{
//		return sensors;
//	}
//
//	public Sensor getSensor(int sensorId)
//	{
//		return sensors[sensorId+1];
//	}
//
//	public void setRoomId(int id)
//	{
//		if (id >= 0)
//		{
//			roomId = id;
//		}
//	}
//
//	public void setSensors(Sensor[] sensors)
//	{
//		this.sensors = sensors;
//	}
//
//	@Override public String toString()
//	{
//		StringBuilder s = new StringBuilder("{" + "roomId = " + roomId + "\nsensors = ");
//		for (Sensor sensor:sensors)
//		{
//			s.append(sensor.toString());
//		}
//
//		s.append("}");
//
//		return s.toString();
//	}
//=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Rooms")
public class Room{

  //@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private String id;
//  @OneToMany(targetEntity = Sensor.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinColumn(name= "room_sensor_fk", referencedColumnName = "id")
//  private List<Sensor> sensors = new ArrayList<>();
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Sensor> sensors;

  public Room()
  {
    sensors = new ArrayList<>();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public List<Sensor> getSensors()
  {
    return sensors;
  }

  public void setSensors(List<Sensor> sensors)
  {
    this.sensors = sensors;
  }
  
  @Override public String toString()
  {
    return "Room{" + "id=" + id + '}';
  }
}
