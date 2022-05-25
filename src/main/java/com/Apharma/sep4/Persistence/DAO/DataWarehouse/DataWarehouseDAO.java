package com.Apharma.sep4.Persistence.DAO.DataWarehouse;

import com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils.DataMapper;
import com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils.DatabaseHelper;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataWarehouseDAO implements iDataWarehouseDAO
{
  private DatabaseHelper<ReadingDTO> helper;
  private ArrayList<ReadingDTO> readings;

  @Resource(name = "jdbcUrl")
  private String jdbcUrl;

  @Resource(name = "username")
  private String username;

  @Resource(name = "password")
  private String password;

  public DataWarehouseDAO() {
    readings = new ArrayList<>();
  }

  private static ReadingDTO createReading(int id, double readingValue, String timeStamp) {
    ReadingDTO newReading = new ReadingDTO();
    newReading.setId(id);
    newReading.setReadingValue(readingValue);
    newReading.setTimeStamp(timeStamp);

    return newReading;
  }

  private DatabaseHelper<ReadingDTO> helper() {
    if (helper == null)
      helper = new DatabaseHelper<>(jdbcUrl, username, password);
    return helper;
  }

  public ArrayList<ReadingDTO> getReadingsPerDay(int date, int sensorId) {
    readings.clear();
    readings.addAll(helper().map(new ReadingMapper(),
        "SELECT *"
            + " FROM fact_sensor_reading r "
            + " JOIN dim_sensors s"
            + " ON r.s_id = s.s_id"
            + " WHERE d_id = ? AND s.sensorid = ?", date, sensorId));
    return readings;
  }

  public ArrayList<ReadingDTO> getReadingsPerWeek(int week, int year) {
    readings.clear();
    readings.addAll(helper().map(new ReadingMapper(),
        "SELECT *"
            + " FROM fact_sensor_reading r"
            + " LEFT JOIN dim_date d"
            + " ON d.d_id = r.d_id "
            + " WHERE week = ? AND year = ?", week, year));
    return readings;
  }



  private static class ReadingMapper implements DataMapper<ReadingDTO>
  {
    public ReadingDTO create(ResultSet rs) throws SQLException
    {
      int id = rs.getInt("fs_id");
      double readingValue = rs.getDouble("readingvalue"); //refactor naming in the tables : reading_value and others
      String timeStamp = rs.getString("t_id");
      return createReading(id,readingValue, timeStamp);
    }
  }
}
