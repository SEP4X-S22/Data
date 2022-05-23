-- DDL - Create Tables for Staging

--Create staging for Dim_Room
CREATE TABLE IF NOT EXISTS stage_dim_rooms (
 RoomId VARCHAR(16) PRIMARY KEY
);

--Create staging for Dim_Sensor
CREATE TABLE IF NOT EXISTS stage_dim_sensors (
 S_ID SERIAL PRIMARY KEY,
 SensorId VARCHAR(10),
 sensorType VARCHAR(10),
 minValue INT,
 maxValue INT
);

--Create staging for Dim_Date
CREATE TABLE IF NOT EXISTS stage_dim_date (
 D_ID INT NOT NULL,
 Date DATE,
 Day INT,
 Week INT,
 Month INT,
 Year INT
);



--Create staging for Fact_SensorReading
CREATE TABLE IF NOT EXISTS stage_fact_sensor_reading (
 ReadingId SERIAL PRIMARY KEY,
 R_ID INT NOT NULL,
 S_ID INT NOT NULL,
 D_ID INT NOT NULL,
 T_ID INT NOT NULL,
 readingValue INT,
 isOverMax BIT,
 isUnderMin BIT
);

--SET FOREIGN KEYS FOR STAGE_
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_0 FOREIGN KEY (R_ID) REFERENCES stage_dim_rooms (R_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_1 FOREIGN KEY (S_ID) REFERENCES stage_dim_Sensors (S_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_2 FOREIGN KEY (D_ID) REFERENCES stage_dim_date (D_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_3 FOREIGN KEY (T_ID) REFERENCES stage_dim_time (T_ID);



-- DML - LOAD TO STAGE

-- ETL

-- Cleanse Data

-- DDl - EDW


--Create staging for Dim_Room
CREATE TABLE IF NOT EXISTS stage_dim_rooms (
 R_ID SERIAL PRIMARY KEY,
 RoomId VARCHAR(10)
);

--Create staging for Dim_Sensor
CREATE TABLE IF NOT EXISTS stage_dim_sensors (
 S_ID SERIAL PRIMARY KEY,
 SensorId VARCHAR(10),
 sensorType VARCHAR(10),
 minValue INT,
 maxValue INT
);

--Create staging for Dim_Date
CREATE TABLE IF NOT EXISTS stage_dim_date (
 D_ID INT NOT NULL,
 Date DATE,
 Day INT,
 Week INT,
 Month INT,
 Year INT
);



--Create staging for Fact_SensorReading
CREATE TABLE IF NOT EXISTS stage_fact_sensor_reading (
 ReadingId SERIAL PRIMARY KEY,
 R_ID INT NOT NULL,
 S_ID INT NOT NULL,
 D_ID INT NOT NULL,
 T_ID INT NOT NULL,
 readingValue INT,
 isOverMax BIT,
 isUnderMin BIT
);

--SET FOREIGN KEYS FOR STAGE_
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_0 FOREIGN KEY (R_ID) REFERENCES stage_dim_rooms (R_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_1 FOREIGN KEY (S_ID) REFERENCES stage_dim_Sensors (S_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_2 FOREIGN KEY (D_ID) REFERENCES stage_dim_date (D_ID);
ALTER TABLE stage_fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_3 FOREIGN KEY (T_ID) REFERENCES stage_dim_time (T_ID);




-- GENERATE DATES

-- GENERATE TIMES

-- DML - EDW

-- INCREMENTAL LOAD / SCHEDULING

-- TESTING