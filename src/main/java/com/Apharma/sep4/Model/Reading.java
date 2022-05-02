package com.Apharma.sep4.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "READINGS")
public class Reading
{
	private @Id @GeneratedValue int id;
	private int readingValue; //"value" is restricted in database, so I changed it
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp; //changed to Date because it can help us filter readings by date/time/timestamp much easier

	public Reading()
	{
	}

	public Reading(int readingValue, Date timeStamp)
	{
		this.readingValue = readingValue;
		this.timeStamp = timeStamp;
	}

	public int getId()
	{
		return id;
	}

	public int getReadingValue()
	{
		return readingValue;
	}

	public Date getTimeStamp()
	{
		return timeStamp;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setReadingValue(int readingValue)
	{
		this.readingValue = readingValue;
	}

	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	@Override public String toString()
	{
		return "{readingId = "+ id +", readingValue = " + readingValue + ", timeStamp = " + timeStamp + '}';
	}
}
