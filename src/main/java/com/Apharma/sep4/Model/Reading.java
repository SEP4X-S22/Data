package com.Apharma.sep4.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

//@Entity
//@Table(name = "READINGS")
//public class Reading
//{
//	private @Id @GeneratedValue int id;
//	private int readingValue; //"value" is restricted in database, so I changed it
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date timeStamp; //changed to Date because it can help us filter readings by date/time/timestamp much easier
//
//	public Reading()
//	{
//	}
//
//	public Reading(int readingValue, Date timeStamp)
//	{
//		this.readingValue = readingValue;
//		this.timeStamp = timeStamp;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
//
//	public int getReadingValue()
//	{
//		return readingValue;
//	}
//
//	public Date getTimeStamp()
//	{
//		return timeStamp;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public void setReadingValue(int readingValue)
//	{
//		this.readingValue = readingValue;
//	}
//
//	public void setTimeStamp(Date timeStamp)
//	{
//		this.timeStamp = timeStamp;
//	}
//
//	@Override public String toString()
//	{
//		return "{readingId = "+ id +", readingValue = " + readingValue + ", timeStamp = " + timeStamp + '}';
//	}
//=======
@Entity
public class Reading
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int readingValue; //"value" is restricted in database, so I changed it
  @Temporal(TemporalType.TIMESTAMP)
  private Date timeStamp; //changed to Date because it can help us filter readings by date/time/timestamp much easier

  public Reading()
  {
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

  public Reading(int readingValue, Date timeStamp)
  {
    this.readingValue = readingValue;
    this.timeStamp = timeStamp;
  }

  @Override public String toString()
  {
    return "Reading{" + "id=" + id + ", readingValue=" + readingValue
        + ", timeStamp=" + timeStamp + '}';
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public int getReadingValue()
  {
    return readingValue;
  }

  public void setReadingValue(int readingValue)
  {
    this.readingValue = readingValue;
  }

  public Date getTimeStamp()
  {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp)
  {
    this.timeStamp = timeStamp;
  }


}
