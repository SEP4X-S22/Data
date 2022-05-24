--***************************       DDL; Create Tables for Staging      *******************************
/***    RESET STAGE    ***/
/*DROP TABLE stage_fact_sensor_reading;
DROP TABLE stage_dim_rooms;
DROP TABLE stage_dim_sensors;*/

/***    RESET DW    ***/
/*DROP TABLE dw_fact_sensor_reading;
DROP TABLE dw_dim_rooms;
DROP TABLE dw_dim_sensors;*/

--Create staging for Dim_Room
CREATE TABLE IF NOT EXISTS stage_dim_rooms (
 RoomId VARCHAR(16) PRIMARY KEY
);

--Create staging for Dim_Sensor
CREATE TABLE IF NOT EXISTS stage_dim_sensors (
 SensorId INT PRIMARY KEY,
 sensorType INT,
 minValue INT,
 maxValue INT
);

--Create staging for Fact_SensorReading
CREATE TABLE IF NOT EXISTS stage_fact_sensor_reading (
 ReadingId SERIAL PRIMARY KEY,
 RoomId VARCHAR(16) NOT NULL,
 SensorId INT NOT NULL,
 readingValue DOUBLE PRECISION,
 timestamp VARCHAR,
 isOverMax BIT,
 isUnderMin BIT
);

--SET FOREIGN KEYS FOR STAGE_fact_sensor_reading
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_0 FOREIGN KEY (RoomId) REFERENCES stage_dim_rooms (RoomId);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_1 FOREIGN KEY (SensorId) REFERENCES stage_dim_sensors (SensorId);



--***************************       DML; LOAD TO STAGE                      *******************************

-- Room; Load to Stage
INSERT INTO stage_dim_rooms
    (RoomId)
    SELECT id
FROM rooms;

--Sensors; Load to Stage
INSERT INTO stage_dim_sensors
    (SensorId,
     sensorType,
     minValue,
     maxValue)
    SELECT
            id,
            sensor_type,
            constraint_min_value,
            constraint_max_value
FROM sensors;


--Readings; Load to Stage
INSERT INTO stage_fact_sensor_reading
    (roomid,
     sensorid,
     readingvalue,
     timestamp)
     SELECT
            s.room_id,
            r.sensor_id,
            r.reading_value,
            r.time_stamp
FROM readings r
inner join sensors s on r.sensor_id = s.id ;


--***************************       Cleanse Data                            *******************************

/*select to_timestamp(timestamp, 'DD/MM/YYYY | HH24:MI:SS')
            From stage_fact_sensor_reading;*/
--DO THIS IN DW DML

-- set constraint if null for each sensorType
--Humidity (PERCENTAGE)
UPDATE stage_dim_sensors
SET minValue = 30
WHERE sensortype = 0 AND minValue = 0;

UPDATE stage_dim_sensors
SET maxValue = 60
WHERE sensortype = 0 AND maxValue = 0;

--CO2 (PPM)
UPDATE stage_dim_sensors
SET minValue = 250
WHERE sensortype = 1 AND minValue = 0;

UPDATE stage_dim_sensors
SET maxValue = 1000
WHERE sensortype = 1 AND maxValue = 0;

--Light (LUX)
UPDATE stage_dim_sensors
SET minValue = 300
WHERE sensortype = 2 AND minValue = 0;

UPDATE stage_dim_sensors
SET maxValue = 500
WHERE sensortype = 2 AND maxValue = 0;

--Temperature (CELSIUS)
UPDATE stage_dim_sensors
SET minValue = 15
WHERE sensortype = 3 AND minValue = 0;

UPDATE stage_dim_sensors
SET maxValue = 30
WHERE sensortype = 3 AND maxValue = 0;


-- set isOverMax and isUnderMin
UPDATE stage_fact_sensor_reading
SET isovermax = '0'
WHERE isovermax IS NOT NULL;

UPDATE stage_fact_sensor_reading
SET isundermin = '0'
WHERE isundermin IS NULL;

UPDATE stage_fact_sensor_reading
SET isovermax = '1'
FROM stage_dim_sensors s
WHERE readingvalue > s.maxValue;

UPDATE stage_fact_sensor_reading
SET isundermin = '1'
FROM stage_dim_sensors s
WHERE readingvalue < s.minValue;

--***************************       DDl; EDW                                *******************************


--Create dw for Dim_Room
CREATE TABLE IF NOT EXISTS dw_dim_rooms (
 R_ID SERIAL PRIMARY KEY,
 RoomId VARCHAR(16)
);

--Create dw for Dim_Sensor
CREATE TABLE IF NOT EXISTS dw_dim_sensors (
 S_ID SERIAL PRIMARY KEY,
 SensorId INT,
 sensorType INT,
 minValue INT,
 maxValue INT
);

--Create dw for Dim_Date
CREATE TABLE IF NOT EXISTS dw_dim_date (
 D_ID INT NOT NULL PRIMARY KEY,
 Date DATE,
 Day INT,
 Week INT,
 Month INT,
 Year INT
);

--Create dw for Dim_Time
CREATE TABLE IF NOT EXISTS dw_dim_time (
 T_ID INT NOT NULL PRIMARY KEY,
 Time TIME,
 Minute INT,
 Hour INT
);


--Create dw for Fact_SensorReading
CREATE TABLE IF NOT EXISTS dw_fact_sensor_reading (
 ReadingId SERIAL PRIMARY KEY,
 R_ID INT NOT NULL,
 S_ID INT NOT NULL,
 D_ID INT NOT NULL,
 T_ID INT NOT NULL,
 readingValue DOUBLE PRECISION,
 timestamp TIMESTAMP,
 isOverMax BIT,
 isUnderMin BIT
);

--SET FOREIGN KEYS FOR dw_fact_sensor_reading
ALTER TABLE dw_fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_0 FOREIGN KEY (R_ID) REFERENCES dw_dim_rooms (R_ID);
ALTER TABLE dw_fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_1 FOREIGN KEY (S_ID) REFERENCES dw_dim_sensors (S_ID);
ALTER TABLE dw_fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_2 FOREIGN KEY (D_ID) REFERENCES dw_dim_date (D_ID);
ALTER TABLE dw_fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_3 FOREIGN KEY (T_ID) REFERENCES dw_dim_time (T_ID);




--***************************       GENERATE DATES                          *******************************

--***************************       GENERATE TIMES                          *******************************

--***************************       DML - EDW                               *******************************

--***************************       INCREMENTAL LOAD / SCHEDULING           *******************************

--***************************       TESTING                                 *******************************