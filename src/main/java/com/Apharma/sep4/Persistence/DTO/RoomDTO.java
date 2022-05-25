package com.Apharma.sep4.Persistence.DTO;

/**
 DTO class made to hold relevant variables of Room model class objects.
 
 @author 4X Data team
 @version 2.0 - 18.05.2022
 @implNote Added field for number of Sensors in the Room. - Aldís Eir Hansen & Claudiu Cordunianu
 */
public class RoomDTO
{
	private String id;
	private long sensorsCount;
	
	/**
	 Two argument constructor for a RoomDTO object.
	 
	 @param id String ID
	 @param sensorsCount Long of number of Sensors in the Room
	 */
	public RoomDTO(String id, long sensorsCount)
	{
		this.id = id;
		this.sensorsCount = sensorsCount;
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
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "RoomDTO {" + "ID = '" + id + '\'' + ", sensorsCount = " + sensorsCount + '}';
	}
}
