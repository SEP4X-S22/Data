package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  @Query("SELECT new com.Apharma.sep4.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp) FROM Room ro JOIN ro.sensors s JOIN s.readings r WHERE s.sensor = ?2 AND ro.id = ?1")
  List<ReadingDTO> getReadingsForRoomIdAndSensorType(int roomId, Sensor.SensorType sensorType);
}

