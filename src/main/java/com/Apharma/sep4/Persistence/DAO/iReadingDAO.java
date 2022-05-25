package com.Apharma.sep4.Persistence.DAO;


public interface iReadingDAO
{
  public void storeNewEntry(int hum, double temp, int co2, int light, String timestamp, String roomId);
}
