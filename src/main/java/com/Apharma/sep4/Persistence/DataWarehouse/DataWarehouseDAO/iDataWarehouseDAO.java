package com.Apharma.sep4.Persistence.DataWarehouse.DataWarehouseDAO;

import com.Apharma.sep4.Persistence.DTO.ReadingDTO;

import java.util.ArrayList;

/**
 Interface defining methods needed to get Readings from the DWH.
 
 @author 4X Data team
 @version 1.2 - 26.05.2022
 @implNote Added Sensor ID to method arguments. - Aldís Eir Hansen & Claudiu Cordunianu
 */
public interface iDataWarehouseDAO
{
  /**
   Abstract method for getting ReadingDTO objects from a Sensor for a specific day from the DWH.
   
   @param date Integer of timestamp
   @param sensorId Integer of Sensor ID
   @return ArrayList of ReadingDTO objects
   */
  ArrayList<ReadingDTO> getReadingsPerDay(int date, int sensorId);
  
  /**
   Abstract method for getting ReadingDTO objects from a Sensor of a specific week in a year from the DWH.
 
   @param week Integer of week number
   @param year Integer of year
   @param sensorId Integer of Sensor ID
   @return ArrayList of ReadingDTO objects
   */
  ArrayList<ReadingDTO> getReadingsPerWeek(int week, int year, int sensorId);
}
