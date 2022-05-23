package com.Apharma.sep4.DAO;

public interface Dao<T>
{
  void storeNewEntry(int hum, double temp, int co2, String timestamp, String roomId);
}
