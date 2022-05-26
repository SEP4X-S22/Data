package com.Apharma.sep4.Persistence.DAO;

import org.springframework.stereotype.Component;

/**
 Interface with method to update entities of the 'Sensors' table in the database.
 
 @author 4X Data team
 @version 1.0 - 23.05.2022
 */
@Component
public interface iSensorDAO
{
	/**
	 Abstract method to alter a row in the 'Sensors' table in the database.
	 
	 @param sensorId Integer ID of the Sensor which is the PK of the table
	 @param minValue Integer value of the new lower threshold constraint
	 @param maxValue Integer value of the new upper threshold constraint
	 */
	void saveConstraints(int sensorId, int minValue, int maxValue);
}
