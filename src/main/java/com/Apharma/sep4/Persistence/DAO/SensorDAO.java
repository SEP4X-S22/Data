package com.Apharma.sep4.Persistence.DAO;

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
	
	@Override public void saveConstraints(int sensorId, int minValue, int maxValue)
	{
		sensorRepo.setSensorConstraints(sensorId, minValue, maxValue);
	}
}
