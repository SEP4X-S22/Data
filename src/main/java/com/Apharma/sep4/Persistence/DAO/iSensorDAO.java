package com.Apharma.sep4.Persistence.DAO;

public interface iSensorDAO
{
	void saveConstraints(int sensorId, int minValue, int maxValue);
}
