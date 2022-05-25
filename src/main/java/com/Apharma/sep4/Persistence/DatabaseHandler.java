package com.Apharma.sep4.Persistence;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import com.Apharma.sep4.Run.WebSocketClient;
import com.Apharma.sep4.WebAPI.Repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 DatabaseHandler class made to initialize and seed the database.
 
 @author 4X Data team
 @author Ib Havn
 @version 3.0 - 24.05.2022
 @implNote Removed seeding of dummy data. - 4X Data team
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
		(basePackages = {"com/Apharma/sep4/Persistence/DAO", "com/Apharma/sep4/Persistence/DTO",
				"com/Apharma/sep4/MiddlePoint", "com/Apharma/sep4/WebAPI/Controllers", "com/Apharma/sep4/WebAPI/Repos",
				"com/Apharma/sep4/Run"})
public class DatabaseHandler
{
	@Bean
  CommandLineRunner initDatabase(RoomRepo roomRepo)
	{
		return args -> createDummyData(roomRepo);
	}
	
	@Bean
	public WebSocketClient getWebSocket()
	{
		return new WebSocketClient("wss://iotnet.teracom.dk/app?token=vnoUcQAAABFpb3RuZXQudGVyYWNvbS5ka-iuwG5H1SHPkGogk2YUH3Y=");
	}
  
  /**
   Method for converting a long into a String timestamp.
   Base code courtesy of Ib Havn, minor adjustments made to get the desired format.
   
   @param ts Long representing Epoch time when reading was taken
   @return String of the specified format timestamp
   */
	private String tsToString(long ts)
	{
		Date date = new Date(ts);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
		return dateFormat.format(date);
	}
	
  /**
   Method for creating and seeding the database with dummy data for testing purposes.
   
   @param roomRepo RoomRepo object called to store the dummy data in the database
   */
	private void createDummyData(RoomRepo roomRepo)
	{
		long now = new Date().getTime();
		long delay = 5 * 60 * 1000;
		tsToString(now + delay);
		
		Sensor temp;
		Sensor light;
		Sensor hum;
		Sensor co2;
		
		Room room = new Room();
		room.setId("0004A30B00E7E072");
		for (int i = 0; i < 2; i++)
		{
			if (i == 1)
			{
				room = new Room();
				room.setId("0004A30B00E7E2E5");
			}
			
			temp = new Sensor();
			temp.setSensorType(Sensor.SensorType.Temperature);
			light = new Sensor();
			light.setSensorType(Sensor.SensorType.Light);
			hum = new Sensor();
			hum.setSensorType(Sensor.SensorType.Humidity);
			co2 = new Sensor();
			co2.setSensorType(Sensor.SensorType.CO2);
			co2.setConstraintMaxValue(1000);
			co2.setConstraintMinValue(250);
			
			for (int j = 0; j < 2; j++)
			{
				Reading temp1 = new Reading((int) (Math.random() * (50 - 1 + 1) + 1),
						tsToString(now + j * delay));
				temp1.setSensor(temp);
				Reading temp2 = new Reading((int) (Math.random() * (50 - 1 + 1) + 1),
						tsToString(now + j * delay));
				temp2.setSensor(light);
				Reading temp3 = new Reading((int) (Math.random() * (700 - 1 + 1) + 1),
						tsToString(now + j * delay));
				temp3.setSensor(co2);
				Reading temp4 = new Reading((int) (Math.random() * (50 - 1 + 1) + 1),
						tsToString(now + j * delay));
				temp4.setSensor(hum);
			}
			
			co2.setRoom(room);
			temp.setRoom(room);
			hum.setRoom(room);
			light.setRoom(room);
			
			roomRepo.save(room);
		}
	}
}
