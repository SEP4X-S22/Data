package com.Apharma.sep4;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoomUnitTest
{
	private Room room1;
	private Room room2;
	private Sensor sensor1;
	private Sensor sensor2;
	private Sensor sensor3;
	private Sensor sensor4;
	private ArrayList<Sensor> sensors;
	
	@BeforeEach public void setUp()
	{
		room1 = new Room();
		room2 = new Room();
		
		sensor1 = new Sensor();
		sensor2 = new Sensor();
		sensor3 = new Sensor();
		sensor4 = new Sensor();
		sensors = new ArrayList<>();
	}
	
	@Test void unsetRoomID()
	{
		assertNull(room1.getId());
	}
	
	@Test void setAndGetRoomID()
	{
		room2.setId("MyID");
		assertEquals("MyID", room2.getId());
	}
	
	@Test void getZeroSensors()
	{
		assertEquals(0, room1.getSensors().size());
	}
	
	@Test void setAndGetFourSensors()
	{
		sensors.add(sensor1);
		sensors.add(sensor2);
		sensors.add(sensor3);
		sensors.add(sensor4);
		room1.setSensors(sensors);
		assertEquals(4, room1.getSensors().size());
	}
	
	@Test void customToStringForRoom()
	{
		sensors.add(sensor1);
		room1.setId("MyID");
		room1.setSensors(sensors);
		assertEquals("Room {ID = MyID}", room1.toString());
	}
}
