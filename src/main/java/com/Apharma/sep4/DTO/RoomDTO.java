package com.Apharma.sep4.DTO;


public class RoomDTO
{
  private String id;
  private long sensorsCount;

  public RoomDTO(String id, long sensorsCount)
  {
    this.id = id;
    this.sensorsCount = sensorsCount;
  }


  @Override public String toString()
  {
    return "RoomDTO{" + "id='" + id + '\'' + ", sensorsCount=" + sensorsCount
        + '}';
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public long getSensorsCount()
  {
    return sensorsCount;
  }

  public void setSensorsCount(long sensorsCount)
  {
    this.sensorsCount = sensorsCount;
  }
}
