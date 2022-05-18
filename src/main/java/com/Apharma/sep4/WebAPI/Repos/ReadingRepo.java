package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  @Query("SELECT new com.Apharma.sep4.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp) "
      + " FROM Room ro "
      + " JOIN ro.sensors s "
      + " JOIN s.readings r "
      + " WHERE s.sensorType = ?2 AND ro.id = ?1")
  List<ReadingDTO> getReadingsForRoomIdAndSensorType(String roomId, Sensor.SensorType sensorType);

  @Query("SELECT new com.Apharma.sep4.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp)"
      + " FROM Room ro"
      + " JOIN ro.sensors s"
      + " JOIN s.readings r"
      + " WHERE s.sensorType = ?2 AND ro.id = ?1 AND r.id = (SELECT max(r.id) FROM s.readings r)")
  ReadingDTO getCurrentReading(String roomId, Sensor.SensorType sensorType);
}

