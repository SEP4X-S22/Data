package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.SensorConstraintsDTO;
import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository public interface SensorRepo extends JpaRepository<Sensor, Integer>
{
  @Query(
      "SELECT new com.Apharma.sep4.DTO.SensorDTO(s.id, s.sensorType, r.readingValue)"
          + " FROM Sensor s" + " JOIN s.readings r"
          + " WHERE room_id = ?1 AND r.id = (SELECT max(r.id) FROM s.readings r)") List<SensorDTO> getRoomSensors(
      String roomId);

  @Query(
      "SELECT new com.Apharma.sep4.DTO.SensorConstraintsDTO(id, constraintMinValue, constraintMaxValue) "
          + " FROM Sensor"
          + " WHERE id = ?1") SensorConstraintsDTO getSensorConstraints(
      int sensorId);

  @Modifying
  @Query("UPDATE Sensor s "
      + " SET s.constraintMinValue =:min, s.constraintMaxValue =:max"
      + " WHERE s.id =:id")
  void setSensorConstraints(@Param(value = "id") int id, @Param(value = "min")double min, @Param(value = "max")double max);
}
