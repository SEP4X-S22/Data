package com.Apharma.sep4.WebAPI.Repos;


import com.Apharma.sep4.Model.Reading;

import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer>
{

//  List<Room> findByRoomId(int roomId);
//  List<Room> findByRoomAndSensor(int roomId, Sensor.SensorType sensorType);
//  List<Room> findByRoomAndSensorAndTimeStampBefore(int room, Sensor.SensorType sensorType, Date timeStamp);

  Room findById(int roomId);

}
