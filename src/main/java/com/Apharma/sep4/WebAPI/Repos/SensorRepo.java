package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepo extends JpaRepository<Sensor, Integer>
{
 @Query("SELECT new com.Apharma.sep4.DTO.SensorDTO(s.id, s.sensor) FROM Sensor s WHERE room_sensor_fk = ?1 ")
 List<SensorDTO> getRoomSensors(int roomId);

}
