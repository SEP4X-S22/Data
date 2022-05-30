package com.Apharma.sep4;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SensorUnitTest
{
	private Room room1;
	private Room room2;
	private Sensor sensor1;
	private Sensor sensor2;
	private Sensor sensor3;
	private Sensor sensor4;
	private Reading reading1;
	private Reading reading2;
	private Reading reading3;
	private ArrayList<Reading> readings;
	
	@BeforeEach public void setUp()
	{
		room1 = new Room();
		room2 = new Room();
		
		sensor1 = new Sensor();
		sensor2 = new Sensor();
		sensor3 = new Sensor();
		sensor4 = new Sensor();
		
		reading1 = new Reading();
		reading2 = new Reading();
		reading3 = new Reading();
		readings = new ArrayList<>();
	}
	
	@Test void unsetSensorID()
	{
		assertEquals(0, sensor1.getId());
	}
	
	@Test void setAndGetSensorID()
	{
		sensor1.setId(123);
		assertEquals(123, sensor1.getId());
	}
	
	@Test void getZeroReadings()
	{
		assertEquals(0, sensor1.getReadings().size());
	}
	
	@Test void setAndGetThreeReadings()
	{
		readings.add(reading1);
		readings.add(reading2);
		readings.add(reading3);
		sensor1.setReadings(readings);
		assertEquals(3, sensor1.getReadings().size());
	}
	
	@Test void getUnsetRoom()
	{
		assertNull(sensor1.getRoom());
	}
	
	@Test void getSensorRoomEqualsRoomOne()
	{
		sensor1.setRoom(room1);
		assertEquals(room1, sensor1.getRoom());
	}
	
	@Test void getSensorRoomDoesNotEqualRoomTwo()
	{
		sensor1.setRoom(room1);
		assertNotEquals(room2, sensor1.getRoom());
	}
	
	@Test void setAndGetSensorTypes()
	{
		sensor1.setSensorType(Sensor.SensorType.Humidity);
		sensor2.setSensorType(Sensor.SensorType.CO2);
		sensor3.setSensorType(Sensor.SensorType.Temperature);
		sensor4.setSensorType(Sensor.SensorType.Light);
		assertEquals(Sensor.SensorType.Humidity, sensor1.getSensorType());
		assertEquals(Sensor.SensorType.CO2, sensor2.getSensorType());
		assertEquals(Sensor.SensorType.Temperature, sensor3.getSensorType());
		assertEquals(Sensor.SensorType.Light, sensor4.getSensorType());
	}
	
	@Test void getUnsetMinAndMaxValue()
	{
		assertEquals(0, sensor1.getConstraintMaxValue());
		assertEquals(0, sensor1.getConstraintMinValue());
	}
	
	@Test void setAndGetMinValue()
	{
		sensor1.setConstraintMinValue(10);
		assertEquals(10, sensor1.getConstraintMinValue());
	}
	
	@Test void setAndGetMaxValue()
	{
		sensor1.setConstraintMaxValue(30);
		assertEquals(30, sensor1.getConstraintMaxValue());
	}
	
	@Test void customToStringForSensor()
	{
		sensor1.setId(42);
		sensor1.setSensorType(Sensor.SensorType.CO2);
		assertEquals("Sensor {ID = 42, sensorType = CO2, readings = }", sensor1.toString());
	}
	
	@Test void stringValuesOfSensorTypeEnum()
	{
		sensor1.setSensorType(Sensor.SensorType.Humidity);
		sensor2.setSensorType(Sensor.SensorType.CO2);
		sensor3.setSensorType(Sensor.SensorType.Temperature);
		sensor4.setSensorType(Sensor.SensorType.Light);
		assertEquals("Humidity", sensor1.getSensorType().name());
		assertEquals("CO2", sensor2.getSensorType().name());
		assertEquals("Temperature", sensor3.getSensorType().name());
		assertEquals("Light", sensor4.getSensorType().name());
	}
}
