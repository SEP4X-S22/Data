package com.Apharma.sep4.DAO;


import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;

import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;


import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;

import com.Apharma.sep4.WebAPI.Repos.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseHandler
{

//	private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);
//
//	@Bean CommandLineRunner initDatabase(RoomRepo readingRepo, UserRepo userRepo)
//	{
//		return args ->
//		{
//			//dummy data
//
//			Sensor[] a1 = createSensors(2000);
//			Sensor[] a2 = createSensors(2000);
//
//			Room r1 = new Room();
//			r1.setSensors(a1);
//			Room r2 = new Room();
//			r2.setSensors(a2);
//
//			log.info("Preloading " + readingRepo.save(r1));
//			log.info("Preloading " + readingRepo.save(r2));
//		};
//	}
//
//	private Sensor[] createSensors(int numberOfReadings)
//	{
//		Sensor.SensorType[] types = {Sensor.SensorType.Humidity, Sensor.SensorType.CO2, Sensor.SensorType.Light,
//				Sensor.SensorType.Temperature};
//		Sensor[] sTemp = new Sensor[3];
//
//		for (int i = 0; i < 4; i++)
//		{
//			sTemp[i] = new Sensor();
//			sTemp[i].setSensorType(types[i]);
//			sTemp[i].setReadings(createReadings(numberOfReadings));
//			sTemp[i].setMaxValue((int) (Math.random() * (60 - 30 + 1) + 1));
//			sTemp[i].setMinValue((int) (Math.random() * (30 - 1 + 1) + 1));
//		}
//
//		return sTemp;
//	}
//
//	private Reading[] createReadings(int number)
//	{
//		long now = new Date().getTime();
//		long delay = 5 * 60 * 1000;
//
//		Reading[] rTemp = new Reading[number-1];
//
//		for (int i = 0; i < number; i++)
//		{
//			rTemp[i] = new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + i * delay));
//		}
//
//		return rTemp;
//	}

  private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Bean CommandLineRunner initDatabase(RoomRepo roomRepo, SensorRepo sensorRepo, UserRepo userRepo, ReadingRepo readingRepo)
  {
    long now = new Date().getTime();
    long delay = 5 * 60 * 1000;
    ArrayList<Sensor> sensors = new ArrayList<>();

    Sensor temp = new Sensor();
    temp.setSensor(Sensor.SensorType.Temperature);

    Sensor light = new Sensor();
    Sensor hum = new Sensor();
    Sensor co2 = new Sensor();
    co2.setSensor(Sensor.SensorType.CO2);

    ArrayList<Reading> tempList = new ArrayList<>();
    ArrayList<Reading> co2List = new ArrayList<>();
    ArrayList<Reading> humList = new ArrayList<>();
    ArrayList<Reading> lightList = new ArrayList<>();

    Room room = new Room();
		Room room2 = new Room();


    return args -> {
      temp.addReading(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + delay)));
      co2.addReading(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + delay)));
      room.addSensor(temp);
      room.addSensor(co2);
      roomRepo.save(room);
			roomRepo.save(room2);
    };
  }

}




      //int room = 1;
      //Room room = new Room();

      //      co2.setSensor(Sensor.SensorType.CO2);
      //      sensorRepo.save(co2);
      //      temp.setSensor(Sensor.SensorType.Temperature);
      //      sensorRepo.save(temp);
      //      hum.setSensor(Sensor.SensorType.Humidity);
      //      sensorRepo.save(hum);
      //      light.setSensor(Sensor.SensorType.Light);
      //      sensorRepo.save(light);

      //roomRepo.save(room);

      //      for (int i = 0; i < 20; i++)
      //      {
      //        if(i==100){
      //          room = storageRoom;
      //        }

      //Version1

      //        log.info("Preloading " + readingRepo.save(
      //            new Reading(room, Reading.SensorType.CO2,
      //                (int) (Math.random() * (50 - 1 + 1) + 1),
      //                new Date(now + i * delay))));
      //        log.info("Preloading " + readingRepo.save(
      //            new Reading(room, Reading.SensorType.Humidity,
      //                (int) (Math.random() * (50 - 1 + 1) + 1),
      //                new Date(now + i * delay))));
      //        log.info("Preloading " + readingRepo.save(
      //            new Reading(room, Reading.SensorType.Light,
      //                (int) (Math.random() * (50 - 1 + 1) + 1),
      //                new Date(now + i * delay))));
      //        log.info("Preloading " + readingRepo.save(
      //            new Reading(room, Reading.SensorType.Temperature,
      //                (int) (Math.random() * (50 - 1 + 1) + 1),
      //                new Date(now + i * delay))));
      //
      //        log.info("Preloading " + tempList.add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1),new Date(now + i * delay),
      //            temp)));
      //        log.info("Preloading " +  co2List.add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1),new Date(now + i * delay),
      //            co2)));
      //        log.info("Preloading " +  humList.add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1),new Date(now + i * delay),
      //            hum)));
      //        log.info("Preloading " +  lightList.add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1),new Date(now + i * delay),
      //            light)));
      //      }
      //
      ////      readingRepo.saveAll(co2List);
      ////      readingRepo.saveAll(humList);
      ////      readingRepo.saveAll(lightList);
      ////      readingRepo.saveAll(tempList);
      //
      //      co2.setReadings(co2List);
      //      co2.setRoom(room);
      //      sensors.add(co2);
      //
      //      temp.setReadings(tempList);
      //      temp.setRoom(room);
      //      sensors.add(temp);
      //
      //      hum.setReadings(humList);
      //      hum.setRoom(room);
      //      sensors.add(hum);
      //
      //      light.setReadings(lightList);
      //      light.setRoom(room);
      //      sensors.add(light);
      //
      //      room.setSensorsList(sensors);
      //
      //      log.info("Preloading " + roomRepo.save(room));
      //    };
//    };
//  }
//}
