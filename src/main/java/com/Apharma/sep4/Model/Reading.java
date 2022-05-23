package com.Apharma.sep4.Model;

import javax.persistence.*;

@Entity
@Table(name = "Readings")
public class Reading
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private double readingValue;
  private String timeStamp;
  @ManyToOne
  @JoinColumn(name = "sensor_id")
  private Sensor sensor;
  
  public Reading()
  {
  }

  public Reading(double readingValue, String timeStamp)
  {
    this.readingValue = readingValue;
    this.timeStamp = timeStamp;
  }
  
  public Reading(double readingValue, String timeStamp, Sensor sensor)
  {
    this.readingValue = readingValue;
    this.timeStamp = timeStamp;
    this.sensor = sensor;
  }
  
  public void setSensor(Sensor sensor)
  {
    this.sensor = sensor;
    sensor.getReadings().add(this);
  }

  public Sensor getSensor()
  {
    return sensor;
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
  
  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Reading reading = (Reading) o;
    return id == reading.id && readingValue == reading.readingValue
        && timeStamp.equals(reading.timeStamp);
  }

  @Override public String toString()
  {
    return "Reading{" + "id=" + id + ", readingValue=" + readingValue
        + ", timeStamp=" + timeStamp + '}';
  }
}
