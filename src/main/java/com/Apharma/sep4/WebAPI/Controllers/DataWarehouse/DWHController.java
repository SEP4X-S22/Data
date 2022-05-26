package com.Apharma.sep4.WebAPI.Controllers.DataWarehouse;

import com.Apharma.sep4.Persistence.DAO.DataWarehouse.DataWarehouseDAO;
import com.Apharma.sep4.Persistence.DAO.DataWarehouse.iDataWarehouseDAO;
import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class DWHController
{
  private iDataWarehouseDAO warehouseDAO;

  public DWHController(iDataWarehouseDAO dataWarehouseDAO){
      this.warehouseDAO = dataWarehouseDAO;
  }

  @GetMapping("/historical/readings/day")
  private ArrayList<ReadingDTO> getReadingsPerDay(@RequestParam int date, @RequestParam int sensorId){
    return warehouseDAO.getReadingsPerDay(date, sensorId);
  }

  @GetMapping("/historical/readings/week")
  private ArrayList<ReadingDTO> getReadingsPerWeek(@RequestParam int week, @RequestParam int year){
    return warehouseDAO.getReadingsPerWeek(week, year);
  }
}
