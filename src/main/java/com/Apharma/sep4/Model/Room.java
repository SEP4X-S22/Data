package com.Apharma.sep4.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 Model class for a Room object. Used as an entity in JPA under the name of 'Rooms'.
 
 @author 4X Data team, Stefan Georgiev
 @version 2.0 - 06.05.2022
 */
@Entity
@Table(name = "Rooms")
public class Room
{
  @Id
  private String id;
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Sensor> sensors;
  
  /**
   Required empty constructor for JPA initializing the 'sensors' field to a new empty ArrayList.
   */
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
  
  /**
   Overridden toString method to alter the default String representation of this class' objects.
   */
  @Override public String toString()
  {
    return "Room {" + "ID = " + id + '}';
  }
}
