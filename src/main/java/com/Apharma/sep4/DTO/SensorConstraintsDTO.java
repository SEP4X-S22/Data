package com.Apharma.sep4.DTO;



public class SensorConstraintsDTO
{
  private int id;
  private double constraintMinValue;
  private double constraintMaxValue;

  public SensorConstraintsDTO(int id, double constraintMinValue,
      double constraintMaxValue)
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

  public double getConstraintMinValue()
  {
    return constraintMinValue;
  }

  public void setConstraintMinValue(double constraintMinValue)
  {
    this.constraintMinValue = constraintMinValue;
  }

  public double getConstraintMaxValue()
  {
    return constraintMaxValue;
  }

  public void setConstraintMaxValue(double constraintMaxValue)
  {
    this.constraintMaxValue = constraintMaxValue;
  }
}
