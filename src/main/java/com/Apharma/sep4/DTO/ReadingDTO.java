package com.Apharma.sep4.DTO;
import java.util.Date;

public class ReadingDTO
{
  private int id;
  private double readingValue;
  private String timeStamp;

  public ReadingDTO(int id, double readingValue, String timeStamp)
  {
    this.id = id;
    this.readingValue = readingValue;
    this.timeStamp = timeStamp;
  }


  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public double getReadingValue()
  {
    return readingValue;
  }

  public void setReadingValue(double readingValue)
  {
    this.readingValue = readingValue;
  }

  public String getTimeStamp()
  {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp)
  {
    this.timeStamp = timeStamp;
  }

  @Override public String toString()
  {
    return "ReadingDTO{" + "id=" + id + ", readingValue=" + readingValue
        + ", timeStamp=" + timeStamp + '}';
  }
}
