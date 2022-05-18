package com.Apharma.sep4.DAO;

import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ReadingDAO implements Dao<Reading>
{
  @Autowired
  private SensorRepo sensorRepo;

  @Autowired
  private RoomRepo roomRepo;

  @Autowired
  private ReadingRepo readingRepo;

  public ReadingDAO()
  {
  }

  public void storeNewEntry(int hum, double temp, int co2, Date timestamp, String roomId)
  {
    Room room = new Room();
    room.setId(roomId);

    //TODO: JPA add to reading without getting all children
   // Room room = roomRepo.getById(roomId);
    List<SensorDTO> sensors = sensorRepo.getRoomSensors(roomId);

    Reading humidity = new Reading(hum, timestamp);
    Reading temperature = new Reading(temp, timestamp);
    Reading co2Reading = new Reading(co2, timestamp);

    for (int i = 0; i < sensors.size(); i++)
    {
      switch (sensors.get(i).getSensorType())
      {
        case Temperature:
        {
          System.out.println(sensors.get(i).getSensorType());
          Sensor temper = new Sensor();
          temper.setId(sensors.get(i).getId());
          temper.setSensorType(sensors.get(i).getSensorType());
          temperature.setSensor(temper);
          temper.setRoom(room);
        }
        break;
        case CO2:
        {
          Sensor cotwo = new Sensor();
          cotwo.setId(sensors.get(i).getId());
          cotwo.setSensorType(sensors.get(i).getSensorType());
          co2Reading.setSensor(cotwo);
          cotwo.setRoom(room);
        }

        break;
        case Humidity:
        {
          System.out.println(sensors.get(i).getSensorType());
          Sensor humi = new Sensor();
          humi.setId(sensors.get(i).getId());
          humi.setSensorType(sensors.get(i).getSensorType());
          humidity.setSensor(humi);
          humi.setRoom(room);
        }
        break;
      }
    }
    roomRepo.save(room);
  }
}
