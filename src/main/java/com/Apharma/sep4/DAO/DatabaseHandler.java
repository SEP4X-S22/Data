package com.Apharma.sep4.DAO;
import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;
import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import com.Apharma.sep4.WebAPI.Repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    return args -> {
      Sensor temp;
      Sensor light;
      Sensor hum;
      Sensor co2;

      Room room;
      for (int i = 0; i < 2; i++)
      {
        room = new Room();

        temp = new Sensor();
        temp.setSensor(Sensor.SensorType.Temperature);
        light = new Sensor();
        light.setSensor(Sensor.SensorType.Light);
        hum = new Sensor();
        hum.setSensor(Sensor.SensorType.Humidity);
        co2 = new Sensor();
        co2.setSensor(Sensor.SensorType.CO2);

      for (int j = 0; j < 1000; j++)
      {
        temp.getReadings().add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + j * delay)));
        co2.getReadings().add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + j * delay)));
        light.getReadings().add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now +j * delay)));
        hum.getReadings().add(new Reading((int) (Math.random() * (50 - 1 + 1) + 1), new Date(now + j *delay)));
      }
      room.getSensorsList().add(co2);
      room.getSensorsList().add(temp);
      room.getSensorsList().add(hum);
      room.getSensorsList().add(light);
      roomRepo.save(room);
      }
    };
  }
}
