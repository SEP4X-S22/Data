/*
Script to Extract, Transform and Load data from the aPHARma source database to a data warrehouse


Written by the Data Team from Group 4X
*/

--***************************       DDL; Create Tables for Staging      *******************************

--Create schema for staging
CREATE SCHEMA IF NOT EXISTS  stage_aPHarma;

--Create schema for DataWarehouse
CREATE SCHEMA IF NOT EXISTS DW_aPHama;

--Create staging for Dim_Room
CREATE TABLE IF NOT EXISTS "stage_aPharma".dim_rooms (
 Room_Id VARCHAR(16) PRIMARY KEY
);

--Create staging for Dim_Sensor
CREATE TABLE IF NOT EXISTS "stage_aPharma".dim_sensors (
 Sensor_Id INT PRIMARY KEY,
 sensor_Type INT,
 min_Value INT,
 max_Value INT
);

--Create staging for Fact_SensorReading
CREATE TABLE IF NOT EXISTS "stage_aPharma".fact_sensor_reading (
 Reading_Id INT PRIMARY KEY,
 Room_Id VARCHAR(16) NOT NULL,
 Sensor_Id INT NOT NULL,
 reading_Value DOUBLE PRECISION,
 timestamp TIMESTAMP,
 is_Over_Max BOOLEAN,
 is_Under_Min BOOLEAN
);

--SET FOREIGN KEYS FOR STAGE_fact_sensor_reading
ALTER TABLE "stage_aPharma".fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_0 FOREIGN KEY (Room_Id) REFERENCES "stage_aPharma".dim_rooms (Room_Id);
ALTER TABLE "stage_aPharma".fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_1 FOREIGN KEY (Sensor_Id) REFERENCES "stage_aPharma".dim_sensors (Sensor_Id);



--***************************       DML; LOAD TO STAGE                      *******************************

-- Room; Load to Stage
INSERT INTO "stage_aPharma".dim_rooms
    (Room_Id)
    SELECT id
FROM public.rooms;

--Sensors; Load to Stage
INSERT INTO "stage_aPharma".dim_sensors
    (Sensor_Id,
     sensor_Type,
     min_Value,
     max_Value)
    SELECT
            id,
            sensor_type,
            constraint_min_value,
            constraint_max_value
FROM public.sensors;


--Readings; Load to Stage
INSERT INTO "stage_aPharma".fact_sensor_reading
    (reading_id,
     room_id,
     sensor_id,
     reading_value,
     timestamp)
     SELECT
            r.id,
            s.room_id,
            r.sensor_id,
            r.reading_value,
            to_timestamp(r.time_stamp, 'DD/MM/YYYY | HH24:MI:SS')
FROM public.readings r
INNER JOIN public.sensors s ON r.sensor_id = s.id ;


--***************************       Cleanse Data                            *******************************

-- set constraint to recommended value if zero for each sensorType
--Humidity (PERCENTAGE)
UPDATE "stage_aPharma".dim_sensors
SET min_Value = 30
WHERE sensor_type = 0 AND min_Value = 0;

UPDATE "stage_aPharma".dim_sensors
SET max_Value = 60
WHERE sensor_type = 0 AND max_Value = 0;

--CO2 (PPM)
UPDATE "stage_aPharma".dim_sensors
SET min_Value = 250
WHERE sensor_type = 1 AND min_Value = 0;

UPDATE "stage_aPharma".dim_sensors
SET max_Value = 1000
WHERE sensor_type = 1 AND max_Value = 0;

--Light (LUX)
UPDATE "stage_aPharma".dim_sensors
SET min_Value = 300
WHERE sensor_type = 2 AND min_Value = 0;

UPDATE "stage_aPharma".dim_sensors
SET max_Value = 500
WHERE sensor_type = 2 AND max_Value = 0;

--Temperature (CELSIUS)
UPDATE "stage_aPharma".dim_sensors
SET min_Value = 15
WHERE sensor_type = 3 AND min_Value = 0;

UPDATE "stage_aPharma".dim_sensors
SET max_Value = 30
WHERE sensor_type = 3 AND max_Value = 0;


-- set isOverMax and isUnderMin
UPDATE "stage_aPharma".fact_sensor_reading
SET is_over_max = '0'
WHERE is_over_max IS  NULL;

UPDATE "stage_aPharma".fact_sensor_reading
SET is_under_min = '0'
WHERE is_under_min IS NULL;

UPDATE "stage_aPharma".fact_sensor_reading fsr
SET is_over_max = '1'
FROM "stage_aPharma".dim_sensors s
WHERE reading_value > s.max_Value AND fsr.sensor_id = s.sensor_id;

UPDATE "stage_aPharma".fact_sensor_reading fsr
SET is_under_min = '1'
FROM "stage_aPharma".dim_sensors s
WHERE reading_value < s.min_Value  AND fsr.sensor_id = s.sensor_id;



--***************************       DDl; EDW                                *******************************


--Create dw for Dim_Room
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_rooms (
 R_ID SERIAL PRIMARY KEY,
 Room_Id VARCHAR(16)
);

--Create dw for Dim_Sensor
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_sensors (
 S_ID SERIAL PRIMARY KEY,
 Sensor_Id INT,
 sensor_Type INT,
 min_Value INT,
 max_Value INT
);

--Create dw for Dim_Date
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_date (
 D_ID INT NOT NULL PRIMARY KEY,
 Date DATE,
 Day INT,
 Week INT,
 Month INT,
 Year INT
);

--Create dw for Dim_Time
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_time (
 T_ID INT NOT NULL PRIMARY KEY,
 Time TIME,
 Second INT,
 Minute INT,
 Hour INT
);

--Create dw for Fact_SensorReading
CREATE TABLE IF NOT EXISTS "DW_aPHarma".fact_sensor_reading (
 FS_ID SERIAL PRIMARY KEY,
 R_ID INT NOT NULL,
 S_ID INT NOT NULL,
 D_ID INT NOT NULL,
 T_ID INT NOT NULL,
 Reading_Id INT,
 reading_Value DOUBLE PRECISION,
 is_Over_Max BOOLEAN default false,
 is_Under_Min BOOLEAN default false
);

--SET FOREIGN KEYS FOR dw_fact_sensor_reading
ALTER TABLE "DW_aPHarma".fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_0 FOREIGN KEY (R_ID) REFERENCES "DW_aPHarma".dim_rooms (R_ID);
ALTER TABLE "DW_aPHarma".fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_1 FOREIGN KEY (S_ID) REFERENCES "DW_aPHarma".dim_sensors (S_ID);
ALTER TABLE "DW_aPHarma".fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_2 FOREIGN KEY (D_ID) REFERENCES "DW_aPHarma".dim_date (D_ID);
ALTER TABLE "DW_aPHarma".fact_sensor_reading ADD CONSTRAINT FK_dw_Fact_SensorReading_3 FOREIGN KEY (T_ID) REFERENCES "DW_aPHarma".dim_time (T_ID);




--***************************       GENERATE DATES                          *******************************
--Create Temp Table for dates
CREATE TEMP TABLE genDate
(
    Date DATE
);

-- 100 years of dates
INSERT INTO genDate(
    (SELECT generatedDate::date
     FROM generate_series
              ('01/01/2020'::timestamp
              , '01/01/2120'::timestamp
              , '1 day'::interval) generatedDate)
);


--***************************       GENERATE TIMES                          *******************************
--Create Temp Table for times
CREATE TEMP TABLE genTime
(
    Time Time
);

-- 1 day of times
INSERT INTO genTime(
    (SELECT dayTime::time
FROM   generate_series('2020-01-01 00:00:00'::timestamp
                     , '2020-01-01 23:59:59'::timestamp
                     , '1 SECOND'::interval) dayTime)
);

--***************************       DML - EDW                               *******************************


--Insert into Dim_date
INSERT INTO "DW_aPHarma".dim_date(D_ID,Date, Day, Week, Month, Year)
SELECT
       (SELECT to_char((Date), 'YYYYMMDD'))::integer,
       Date,
       EXTRACT(day FROM DATE),
       EXTRACT(week FROM DATE),
       EXTRACT(month FROM DATE),
       EXTRACT(year FROM DATE)
FROM genDate;

--Insert into Dim_Time
INSERT INTO "DW_aPHarma".dim_time(T_ID, Time, Second ,Minute ,Hour)
SELECT
       (SELECT to_char((Time), 'SSSS'))::integer,
       Time,
       EXTRACT(Second FROM Time),
       EXTRACT(Minute FROM Time),
       EXTRACT(Hour FROM Time)
FROM genTime;

--Inserting the rooms
INSERT INTO "DW_aPHarma".dim_rooms
    (room_id)
SELECT
       room_id
FROM "stage_aPharma".dim_rooms;

--Inserting the sensors
INSERT INTO "DW_aPHarma".dim_sensors
    ( sensor_id,
     sensor_type,
     min_value,
     max_value)
SELECT
       sensor_id,
       sensor_type,
       min_value,
       max_value
FROM "stage_aPharma".dim_sensors;

--Inserting into Fact table
INSERT INTO "DW_aPHarma".fact_sensor_reading
    (r_id,
     s_id,
     d_id,
     t_id,
     reading_id,
     reading_value,
     is_over_max,
     is_under_min)
SELECT
       dr.R_ID,
       ds.S_ID,
       (SELECT to_char((select timestamp :: date), 'YYYYMMDD')::integer),
       (SELECT to_char((select timestamp :: time), 'SSSS')::integer),
       fsr.reading_id,
       fsr.reading_value,
       fsr.is_over_max,
       fsr.is_under_min
FROM "stage_aPharma".fact_sensor_reading fsr
JOIN "DW_aPHarma".dim_sensors ds on ds.sensor_id = fsr.sensor_id
JOIN "DW_aPHarma".dim_rooms dr on dr.room_id = fsr.room_id;


--DROP TEMP TABLE
DROP TABLE genDate;
DROP TABLE genTime;