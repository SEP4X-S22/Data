package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Integer>
{
 @Query("SELECT new com.Apharma.sep4.DTO.SensorDTO(s.id, s.sensorType) FROM Sensor s WHERE room_id = ?1 ")
 List<SensorDTO> getRoomSensors(int roomId);
}
