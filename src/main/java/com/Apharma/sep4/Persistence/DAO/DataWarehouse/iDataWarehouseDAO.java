package com.Apharma.sep4.Persistence.DAO.DataWarehouse;

import com.Apharma.sep4.Persistence.DTO.ReadingDTO;

import java.util.ArrayList;

public interface iDataWarehouseDAO
{
  public ArrayList<ReadingDTO> getReadingsPerDay(int date, int sensorId);
  public ArrayList<ReadingDTO> getReadingsPerWeek(int week, int year, int sensorId);
}
