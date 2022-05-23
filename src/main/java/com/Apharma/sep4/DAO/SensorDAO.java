package com.Apharma.sep4.DAO;

import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorDAO implements iSensorDAO
{
	@Autowired
	private SensorRepo sensorRepo;
	
	
	public SensorDAO()
	{
	
	}
	
	@Override public void saveConstraints(int sensorId, double minValue, double maxValue)
	{
		sensorRepo.setSensorConstraints(sensorId, minValue, maxValue);
	}
}
