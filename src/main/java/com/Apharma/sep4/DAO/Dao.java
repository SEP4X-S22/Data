package com.Apharma.sep4.DAO;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface Dao<T>
{
//  Optional<T> get(int id);
//  Collection<T> getAll();
//  int save(T t);
//void delete(T t);

  void storeNewEntry(int hum, double temp, int co2, String timestamp, String roomId);
}
