package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  @Query("SELECT new com.Apharma.sep4.DTO.ReadingDTO(r.id, r.readingValue, r.timeStamp) FROM Readings r JOIN r.sensor s JOIN s.room ro WHERE s.sensor = ?2 AND ro.id = ?1")
  List<ReadingDTO> getReadingsForRoomIdAndSensorType(int roomId, Sensor.SensorType sensorType);
}
