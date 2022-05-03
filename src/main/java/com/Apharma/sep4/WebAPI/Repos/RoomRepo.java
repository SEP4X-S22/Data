package com.Apharma.sep4.WebAPI.Repos;

<<<<<<< Updated upstream
import com.Apharma.sep4.Model.Reading;
=======
>>>>>>> Stashed changes
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream
import java.util.Date;
=======
>>>>>>> Stashed changes
import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer>
{
<<<<<<< Updated upstream
  List<Room> findByRoomId(int roomId);
  List<Room> findByRoomAndSensor(int roomId, Sensor.SensorType sensorType);
  List<Room> findByRoomAndSensorAndTimeStampBefore(int room, Sensor.SensorType sensorType, Date timeStamp);
=======
  Room findById(int roomId);
>>>>>>> Stashed changes
}
