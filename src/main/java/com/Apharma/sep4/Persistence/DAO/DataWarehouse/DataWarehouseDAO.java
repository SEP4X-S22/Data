package com.Apharma.sep4.Persistence.DAO.DataWarehouse;

import com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils.DataMapper;
import com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils.DatabaseHelper;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 Implementation class of iDataWarehouseDAO.
 
 @author 4X Data team
 @author Ole Ildsgaard Hougaard
 @version 1.2 - 26.05.2022
 @implNote Added Sensor ID to method arguments. - Aldís Eir Hansen & Claudiu Cordunianu
 */
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
	
  /**
   No argument constructor for a DataWarehouseDAO.
   Initializes the 'readings' field to a new empty ArrayList.
   */
	public DataWarehouseDAO()
	{
		readings = new ArrayList<>();
	}
  
  /**
   Method courtesy of Ole Ildsgaard Hougaard. Alterations made to fit this project.
   
   @param id Integer value of Reading ID
   @param readingValue Double of the recorded reading value
   @param timestamp String timestamp of when the data was sent from LoRaWAN
   */
	private static ReadingDTO createReading(int id, double readingValue, String timestamp)
	{
		ReadingDTO newReading = new ReadingDTO();
		newReading.setId(id);
		newReading.setReadingValue(readingValue);
		newReading.setTimestamp(timestamp);
		
		return newReading;
	}
	
  /**
   Method courtesy of Ole Ildsgaard Hougaard.
   Minor alterations made to fit this project.
   */
	private DatabaseHelper<ReadingDTO> helper()
	{
    if (helper == null)
    {
      helper = new DatabaseHelper<>(jdbcUrl, username, password);
    }
		return helper;
	}
  
  /**
   Method for querying the DWH for ReadingDTO objects from a Sensor for a specific day.
   
   @param date Integer of timestamp
   @param sensorId Integer of Sensor ID
   @return ArrayList of ReadingDTO objects
   */
	public ArrayList<ReadingDTO> getReadingsPerDay(int date, int sensorId)
	{
		readings.clear();
		readings.addAll(helper().map(new ReadingMapper(),
				"SELECT *"
						+ " FROM fact_sensor_reading r "
						+ " JOIN dim_sensors s"
						+ " ON r.s_id = s.s_id"
						+ " WHERE d_id = ? AND s.sensor_id = ?", date, sensorId));
		return readings;
	}
  
  /**
   Method for querying the DWH for ReadingDTO objects from a Sensor of a specific week in a year.
   
   @param week Integer of week number
   @param year Integer of year
   @param sensorId Integer of Sensor ID
   @return ArrayList of ReadingDTO objects
   */
	public ArrayList<ReadingDTO> getReadingsPerWeek(int week, int year, int sensorId)
	{
		readings.clear();
		readings.addAll(helper().map(new ReadingMapper(),
				"SELECT *"
						+ " FROM fact_sensor_reading r"
						+ " LEFT JOIN dim_date d "
						+ " ON d.d_id = r.d_id"
						+ " LEFT JOIN dim_sensors s"
						+ " ON r.s_id = s.s_id "
						+ " WHERE week = ? AND year = ? AND s.sensor_id = ?", week, year, sensorId));
		return readings;
	}
	
  /**
   Custom class to interact with the DWH.
   Courtesy of Ole Ildsgaard Hougaard. Only alterations made to the original are customizations to fit this project.
   
   @author Ole Ildsgaard Hougaard
   @author 4X Data team
   @version 1.0 - 25.05.2022
   */
	private static class ReadingMapper implements DataMapper<ReadingDTO>
	{
		public ReadingDTO create(ResultSet rs) throws SQLException
		{
			int id = rs.getInt("fs_id");
			double readingValue = rs.getDouble("reading_value");
			String timeStamp = rs.getString("t_id");
			return createReading(id, readingValue, timeStamp);
		}
	}
}
