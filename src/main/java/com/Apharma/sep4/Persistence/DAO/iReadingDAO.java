package com.Apharma.sep4.Persistence.DAO;

public interface iReadingDAO
{
  void storeNewEntry(int hum, double temp, int co2, String timestamp, String roomId);
}
