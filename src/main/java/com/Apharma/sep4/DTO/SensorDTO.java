package com.Apharma.sep4.DTO;

import com.Apharma.sep4.Model.Sensor;

public class SensorDTO
{
  private int id;
  private Sensor.SensorType sensorType;

  public SensorDTO(int id, Sensor.SensorType sensor)
  {
    this.id = id;
    this.sensorType = sensor;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public Sensor.SensorType getSensor()
  {
    return sensorType;
  }

  public void setSensor(Sensor.SensorType sensor)
  {
    this.sensorType = sensor;
  }

  @Override public String toString()
  {
    return "SensorDTO{" + "id=" + id + ", sensor=" + sensorType + '}';
  }
}
