package com.Apharma.sep4.DAO;

import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import com.Apharma.sep4.WebAPI.Repos.UserRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

  public void storeNewEntry(int hum, int temp, int co2, Date timestamp, int roomId)
  {

    Room room = roomRepo.getById(roomId);

    List<SensorDTO> sensors = sensorRepo.getRoomSensors(roomId);

    Reading humidity = new Reading(hum, timestamp);
    Reading temperature = new Reading(temp, timestamp);
    Reading co2Reading = new Reading(co2, timestamp);

    System.out.println("here" + sensors);
    for (int i = 0; i < sensors.size(); i++)
    {
      switch (sensors.get(i).getSensor())
      {
        case Temperature:
        {
          System.out.println(sensors.get(i).getSensor());
          Sensor temper = sensorRepo.getById(sensors.get(i).getId());
          temperature.setSensor(temper);
          readingRepo.save(temperature);
        }
        break;
        case CO2:
        {
          System.out.println(sensors.get(i).getSensor());
          Sensor cotwo = sensorRepo.getById(sensors.get(i).getId());;
          co2Reading.setSensor(cotwo);
          readingRepo.save(co2Reading);
        }
        break;
        case Humidity:
        {
          System.out.println(sensors.get(i).getSensor());
          Sensor humi = sensorRepo.getById(sensors.get(i).getId());;
          humidity.setSensor(humi);
          readingRepo.save(humidity);
        }
        break;
      }
    }
  }
}
