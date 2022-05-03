package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepo extends JpaRepository<Sensor, Integer>
{
 List<Sensor> findByRoomIdEquals(int roomId);
}
