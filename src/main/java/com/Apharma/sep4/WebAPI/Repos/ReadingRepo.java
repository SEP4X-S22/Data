package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.Persistence.DTO.ReadingDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 Repository interface for querying the Reading entity table in the database.
 
 @author 4X Data team
 @version 2.0 - 18.05.2022
 @implNote Added query method for getting the latest Reading. - Claudiu Cordunianu
 */
@Repository
public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  /**
   Abstract method for querying the database for Readings from a Room's Sensor of a specific type.
   
   @param roomId String ID of the Room
   @param sensorType Enum of the SensorType
   @return List of Readings represented by ReadingDTO objects
   */
  @Query("SELECT new com.Apharma.sep4.Persistence.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp) "
      + " FROM Room ro "
      + " JOIN ro.sensors s "
      + " JOIN s.readings r "
      + " WHERE s.sensorType = ?2 AND ro.id = ?1")
  List<ReadingDTO> getReadingsForRoomIdAndSensorType(String roomId, Sensor.SensorType sensorType);
  
  /**
   Abstract method for querying the database for the latest Reading from a Room's Sensor of a specific type.
   
   @param roomId String ID of the Room
   @param sensorType Enum of the SensorType
   @return Reading represented by a ReadingDTO object
   */
  @Query("SELECT new com.Apharma.sep4.Persistence.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp)"
      + " FROM Room ro"
      + " JOIN ro.sensors s"
      + " JOIN s.readings r"
      + " WHERE s.sensorType = ?2 AND ro.id = ?1 AND r.id = (SELECT max(r.id) FROM s.readings r)")
  ReadingDTO getCurrentReading(String roomId, Sensor.SensorType sensorType);
}

