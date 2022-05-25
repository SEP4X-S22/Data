package com.Apharma.sep4.Persistence.DTO;

/**
 DTO class made to hold relevant variables of Reading model class objects.
 
 @author 4X Data team
 @version 1.1 - 18.05.2022
 @implNote Changed 'timestamp' field data type from Date to String. - Aldís Eir Hansen
 */
public class ReadingDTO
{
	private int id;
	private double readingValue;
	private String timestamp;
	
	/**
	 Three argument constructor for a ReadingDTO.
	 
	 @param id Integer ID value
	 @param readingValue Double of the recorded reading value
	 @param timestamp String timestamp of when the data was sent from LoRaWAN
	 */
	public ReadingDTO(int id, double readingValue, String timestamp)
	{
		this.id = id;
		this.readingValue = readingValue;
		this.timestamp = timestamp;
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
	
	public String getTimestamp()
	{
		return timestamp;
	}
	
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "ReadingDTO {" + "ID = " + id + ", readingValue = " + readingValue + ", timestamp = " + timestamp + '}';
	}
}
