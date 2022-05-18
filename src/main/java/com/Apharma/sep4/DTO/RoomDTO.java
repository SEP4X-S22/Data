package com.Apharma.sep4.DTO;

public class RoomDTO
{
  private int id;

  public RoomDTO(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }
  
  @Override public String toString()
  {
    return "RoomDTO{" + "id=" + id + '}';
  }
}
