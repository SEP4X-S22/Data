package com.Apharma.sep4.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Rooms")
public class Room
{
  @Id
  private String id;
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
    return "Room {" + "id = " + id + '}';
  }
}
