package com.Apharma.sep4.DAO;

import com.Apharma.sep4.WebAPI.Repos.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorDAO implements iSensorDAO
{
	@Autowired
	private SensorRepo sensorRepo;
	
	@Override public void storeNewConstraints(int id, double min, double max)
	{
		sensorRepo.setSensorConstraints(id, min, max);
	}
}
