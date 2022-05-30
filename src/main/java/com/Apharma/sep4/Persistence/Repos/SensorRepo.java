package com.Apharma.sep4.Persistence.Repos;

import com.Apharma.sep4.Persistence.DTO.SensorDTO;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Repository interface for querying the Sensor entity table in the database.
 
 @author 4X Data team
 @version 2.1 - 26.05.2022
 @implNote Removed unused method. - Claudiu Cordunianu & Aldís Eir Hansen
 */
@Transactional
@Repository public interface SensorRepo extends JpaRepository<Sensor, Integer>
{
  /**
   Abstract method for querying the database for Sensors from a specific Room.
   
   @param roomId String ID of the Room
   @return List of Sensors represented by SensorDTO objects
   */
  @Query("SELECT new com.Apharma.sep4.Persistence.DTO.SensorDTO(s.id, s.sensorType, s.constraintMinValue, s.constraintMaxValue, r.readingValue)"
          + " FROM Sensor s"
          + " JOIN s.readings r"
          + " WHERE room_id = ?1 AND r.id = (SELECT max(r.id) FROM s.readings r)")
  List<SensorDTO> getRoomSensors(String roomId);
  
  /**
   Abstract method for querying the database to modify a specific Sensors' minimum and maximum threshold constraints.
   
   @param id Integer ID of the Sensor
   @param min Integer of the new minimum threshold constraint
   @param max Integer of the new maximum threshold constraint
   */
  @Modifying
  @Query("UPDATE Sensor s "
      + " SET s.constraintMinValue = :min, s.constraintMaxValue = :max"
      + " WHERE s.id = :id")
  void setSensorConstraints(@Param(value = "id") int id, @Param(value = "min")int min, @Param(value = "max")int max);
  
  /**
   Abstract method for querying the database for the ID of the Room that has a specific Sensor.
   
   @param sensorId Integer ID of the Sensor
   @return String ID of the Room
   */
  @Query("SELECT r.id"
      + " FROM Room r"
      + " JOIN r.sensors s"
      + " WHERE s.id = ?1")
  String getRoomIdBySensorId(int sensorId);
}
