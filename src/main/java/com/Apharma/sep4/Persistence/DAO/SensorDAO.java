package com.Apharma.sep4.Persistence.DAO;

import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 Implementation class of iSensorDAO.
 
 @author 4X Data team
 @version 1.0 - 23.05.2022
 */
@Component
public class SensorDAO implements iSensorDAO
{
	@Autowired
	private SensorRepo sensorRepo;
	
	/**
	 Required empty constructor for JPA.
	 */
	public SensorDAO()
	{
	
	}
	
	/**
	 Implementation of interface method to alter a row in the 'Sensors' table in the database calling the SensorRepo.
	 
	 @param sensorId Integer ID of the Sensor
	 @param minValue Integer value of the new lower threshold constraint
	 @param maxValue Integer value of the new upper threshold constraint
	 */
	@Override public void saveConstraints(int sensorId, int minValue, int maxValue)
	{
		sensorRepo.setSensorConstraints(sensorId, minValue, maxValue);
	}
}
