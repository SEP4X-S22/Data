package com.Apharma.sep4.Persistence.DAO;

/**
 Interface with method needed to store new Reading entries into the database.
 
 @author 4X Data team
 @version 2.0 - 24.05.2022
 @implNote Changed name from 'Dao' of data type T to iReadingDAO on addition of another DAO that didn't need the same
 method. - Aldís Eir Hansen
 */
public interface iReadingDAO
{
  /**
   Abstract method to add a new row to the 'Readings' table in the database.
   
   @param hum Integer value of reading from the Humidity sensor
   @param temp Integer value of reading from the Temperature sensor
   @param co2 Integer value of reading from the CO2 sensor
   @param timestamp String representing the timestamp of when the data was collected
   @param roomId String ID of the Room object the Sensor whose Reading this is
   */
  void storeNewEntry(int hum, double temp, int co2, String timestamp, String roomId);
}
