package com.Apharma.sep4.DTO;

public class RoomDTO
{
  private String id;

  public RoomDTO(String id)
  {
    this.id = id;
  }

  @Override public String toString()
  {
    return "RoomDTO{" + "id=" + id + '}';
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }
}
