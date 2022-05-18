package com.Apharma.sep4.DTO;

import com.Apharma.sep4.Model.Sensor;

public class SensorDTO
{
  private int id;
  private Sensor.SensorType sensorType;

  public SensorDTO(int id, Sensor.SensorType sensorType)
  {

    this.id = id;
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

  public Sensor.SensorType getSensorType()
  {
    return sensorType;
  }

  public void setSensorType(Sensor.SensorType sensorType)
  {
    this.sensorType = sensorType;
  }

  @Override public String toString()
  {
    return "SensorDTO{" + "id=" + id + ", sensor=" + sensorType + '}';
  }
}
