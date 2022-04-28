package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.Model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  List<Reading> findByRoomAndSensor(int room, Reading.SensorType sensorType);
  List<Reading> findByRoomAndSensorAndTimeStampBefore(int room, Reading.SensorType sensorType, Date timeStamp);
}
