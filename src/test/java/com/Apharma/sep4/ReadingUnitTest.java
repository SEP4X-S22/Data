package com.Apharma.sep4;

import com.Apharma.sep4.Model.Reading;
import com.Apharma.sep4.Model.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReadingUnitTest
{
	private Sensor sensor1;
	private Sensor sensor2;
	private Reading reading1;
	private Reading reading2;
	private Reading reading3;
	private ArrayList<Reading> readings;
	
	@BeforeEach public void setUp()
	{
		sensor1 = new Sensor();
		sensor2 = new Sensor();
		
		reading1 = new Reading();
		reading2 = new Reading();
		reading3 = new Reading();
		readings = new ArrayList<>();
	}
	
	@Test void getConstructorSetValueAndTimestamp()
	{
		Reading reading = new Reading(25, "20/12/2022 | 13:07");
		assertEquals(25, reading.getReadingValue());
		assertEquals("20/12/2022 | 13:07", reading.getTimeStamp());
	}
	
	@Test void getConstructorSetValueTimestampAndSensor()
	{
		Reading reading = new Reading(25, "20/12/2022 | 13:07", sensor1);
		assertEquals(25, reading.getReadingValue());
		assertEquals("20/12/2022 | 13:07", reading.getTimeStamp());
		assertEquals(sensor1, reading.getSensor());
	}
	
	@Test void getUnsetReadingID()
	{
		assertEquals(0, reading1.getId());
	}
	
	@Test void setAndGetReadingID()
	{
		reading1.setId(82);
		assertEquals(82, reading1.getId());
	}
	
	@Test void getUnsetSensor()
	{
		assertNull(reading1.getSensor());
	}
	
	@Test void getSensorEqualsSensorOne()
	{
		sensor1.setId(1);
		sensor1.setSensorType(Sensor.SensorType.Light);
		sensor2.setId(2);
		sensor2.setSensorType(Sensor.SensorType.CO2);
		reading1.setSensor(sensor1);
		assertEquals(sensor1, reading1.getSensor());
	}
	
	@Test void getSensorDoesNotEqualSensorTwo()
	{
		sensor1.setId(1);
		sensor1.setSensorType(Sensor.SensorType.Light);
		sensor2.setId(2);
		sensor2.setSensorType(Sensor.SensorType.CO2);
		reading1.setSensor(sensor1);
		assertNotEquals(sensor2, reading1.getSensor());
	}
	
	@Test void getUnsetReadingValue()
	{
		assertEquals(0, reading1.getReadingValue());
	}
	
	@Test void setAndGetReadingValue()
	{
		reading1.setReadingValue(-5);
		assertEquals(-5.0, reading1.getReadingValue());
	}
	
	@Test void getUnsetTimestamp()
	{
		assertNull(reading1.getTimeStamp());
	}
	
	@Test void setAndGetTimestamp()
	{
		reading1.setTimeStamp("01/01/2022");
		assertEquals("01/01/2022", reading1.getTimeStamp());
	}
	
	@Test void customToStringForReading()
	{
		reading1.setId(9000);
		reading1.setReadingValue(100);
		reading1.setTimeStamp("01/01/2022");
		reading1.setSensor(sensor1);
		assertEquals("Reading {ID = 9000, readingValue = 100.0, timestamp = 01/01/2022}", reading1.toString());
	}
}
