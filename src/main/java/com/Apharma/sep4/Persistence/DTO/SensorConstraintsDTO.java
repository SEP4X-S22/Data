package com.Apharma.sep4.Persistence.DTO;

/**
 DTO class made to hold relevant variables of Sensor model class objects.
 
 @author Claudiu Cordunianu
 @version 1.1 - 25.05.2022
 @implNote Added toString method. - Aldís Eir Hansen
 */
public class SensorConstraintsDTO
{
	private int id;
	private int constraintMinValue;
	private int constraintMaxValue;
	
	public SensorConstraintsDTO(int id, int constraintMinValue, int constraintMaxValue)
	{
		this.id = id;
		this.constraintMinValue = constraintMinValue;
		this.constraintMaxValue = constraintMaxValue;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getConstraintMinValue()
	{
		return constraintMinValue;
	}
	
	public void setConstraintMinValue(int constraintMinValue)
	{
		this.constraintMinValue = constraintMinValue;
	}
	
	public int getConstraintMaxValue()
	{
		return constraintMaxValue;
	}
	
	public void setConstraintMaxValue(int constraintMaxValue)
	{
		this.constraintMaxValue = constraintMaxValue;
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "SensorDTO {" + "ID = " + id + ", constraintMinValue = " + constraintMinValue + ", constraintMaxValue = "
							 + constraintMaxValue + '}';
	}
}
