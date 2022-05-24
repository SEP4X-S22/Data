package com.Apharma.sep4.Persistence.DTO;

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
}
