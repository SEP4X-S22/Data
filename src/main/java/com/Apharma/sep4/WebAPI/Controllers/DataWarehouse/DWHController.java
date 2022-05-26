package com.Apharma.sep4.WebAPI.Controllers.DataWarehouse;

import com.Apharma.sep4.Persistence.DataWarehouse.iDataWarehouseDAO;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 Controller class for handling requests to the DWH that deal with historical Readings.
 
 @author 4X Data team
 @version 1.1 - 26.05.2022
 */
@RestController
public class DWHController
{
  private iDataWarehouseDAO warehouseDAO;

  /**
   One argument constructor for a DWHController.

   @param dataWarehouseDAO Implementation of iDataWarehouseDAO
   */
	public DWHController(iDataWarehouseDAO dataWarehouseDAO)
	{
		this.warehouseDAO = dataWarehouseDAO;
	}

	/**
	 Method exposing the endpoint for getting ReadingDTO objects from a Sensor for a specific day.
	 
	 @param date Integer of timestamp
	 @param sensorId Integer of Sensor ID
	 @return List of ReadingDTO objects from the DWH
	 */
	@GetMapping("/readings/day")
	private ArrayList<ReadingDTO> getReadingsPerDay(@RequestParam int date, @RequestParam int sensorId)
	{
		return warehouseDAO.getReadingsPerDay(date, sensorId);
	}
	
	/**
	 Method exposing the endpoint for getting ReadingDTO objects from a Sensor of a specific week in a year.
	 
	 @param week Integer of week number
	 @param year Integer of year
	 @param sensorId Integer of Sensor ID
	 @return List of ReadingDTO objects from the DWH
	 */
	@GetMapping("/readings/week")
	private ArrayList<ReadingDTO> getReadingsPerWeek(@RequestParam int week, @RequestParam int year, @RequestParam int sensorId)
	{
		return warehouseDAO.getReadingsPerWeek(week, year, sensorId);
	}
}
