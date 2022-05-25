--***************************       DDL; Create Tables for Staging      *******************************
/***    RESET STAGE    ***/
/*DROP TABLE "stage_aPharma".fact_sensor_reading;
DROP TABLE "stage_aPharma".dim_rooms;
DROP TABLE "stage_aPharma".dim_sensors;*/

/***    RESET DW    ***/
/*DROP TABLE "DW_aPHarma".fact_sensor_reading;
DROP TABLE "DW_aPHarma".dim_rooms;
DROP TABLE "DW_aPHarma".dim_sensors;
DROP TABLE "DW_aPHarma".dim_date;
DROP TABLE "DW_aPHarma".dim_time;*/

--Create staging for Dim_Room
CREATE TABLE IF NOT EXISTS "stage_aPharma".dim_rooms (
 RoomId VARCHAR(16) PRIMARY KEY
);

--Create staging for Dim_Sensor
CREATE TABLE IF NOT EXISTS "stage_aPharma".dim_sensors (
 SensorId INT PRIMARY KEY,
 sensorType INT,
 minValue INT,
 maxValue INT
);

--Create staging for Fact_SensorReading
CREATE TABLE IF NOT EXISTS "stage_aPharma".fact_sensor_reading (
 ReadingId SERIAL PRIMARY KEY,
 RoomId VARCHAR(16) NOT NULL,
 SensorId INT NOT NULL,
 readingValue DOUBLE PRECISION,
 timestamp VARCHAR,
 isOverMax BIT,
 isUnderMin BIT
);

--SET FOREIGN KEYS FOR STAGE_fact_sensor_reading
ALTER TABLE "stage_aPharma".fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_0 FOREIGN KEY (RoomId) REFERENCES "stage_aPharma".dim_rooms (RoomId);
ALTER TABLE "stage_aPharma".fact_sensor_reading ADD CONSTRAINT FK_Stage_Fact_SensorReading_1 FOREIGN KEY (SensorId) REFERENCES "stage_aPharma".dim_sensors (SensorId);



--***************************       DML; LOAD TO STAGE                      *******************************

-- Room; Load to Stage
INSERT INTO "stage_aPharma".dim_rooms
    (RoomId)
    SELECT id
FROM public.rooms;

--Sensors; Load to Stage
INSERT INTO "stage_aPharma".dim_sensors
    (SensorId,
     sensorType,
     minValue,
     maxValue)
    SELECT
            id,
            sensor_type,
            constraint_min_value,
            constraint_max_value
FROM public.sensors;


--Readings; Load to Stage
INSERT INTO "stage_aPharma".fact_sensor_reading
    (roomid,
     sensorid,
     readingvalue,
     timestamp)
     SELECT
            s.room_id,
            r.sensor_id,
            r.reading_value,
            r.time_stamp
FROM public.readings r
inner join public.sensors s on r.sensor_id = s.id ;


--***************************       Cleanse Data                            *******************************

/*select to_timestamp(timestamp, 'DD/MM/YYYY | HH24:MI:SS')
            From stage_fact_sensor_reading;*/
--DO THIS IN DW DML

-- set constraint if null for each sensorType
--Humidity (PERCENTAGE)
UPDATE "stage_aPharma".dim_sensors
SET minValue = 30
WHERE sensortype = 0 AND minValue = 0;

UPDATE "stage_aPharma".dim_sensors
SET maxValue = 60
WHERE sensortype = 0 AND maxValue = 0;

--CO2 (PPM)
UPDATE "stage_aPharma".dim_sensors
SET minValue = 250
WHERE sensortype = 1 AND minValue = 0;

UPDATE "stage_aPharma".dim_sensors
SET maxValue = 1000
WHERE sensortype = 1 AND maxValue = 0;

--Light (LUX)
UPDATE "stage_aPharma".dim_sensors
SET minValue = 300
WHERE sensortype = 2 AND minValue = 0;

UPDATE "stage_aPharma".dim_sensors
SET maxValue = 500
WHERE sensortype = 2 AND maxValue = 0;

--Temperature (CELSIUS)
UPDATE "stage_aPharma".dim_sensors
SET minValue = 15
WHERE sensortype = 3 AND minValue = 0;

UPDATE "stage_aPharma".dim_sensors
SET maxValue = 30
WHERE sensortype = 3 AND maxValue = 0;


-- set isOverMax and isUnderMin
UPDATE "stage_aPharma".fact_sensor_reading
SET isovermax = '0'
WHERE isovermax IS NOT NULL;

UPDATE "stage_aPharma".fact_sensor_reading
SET isundermin = '0'
WHERE isundermin IS NULL;

UPDATE "stage_aPharma".fact_sensor_reading
SET isovermax = '1'
FROM "stage_aPharma".dim_sensors s
WHERE readingvalue > s.maxValue;

UPDATE "stage_aPharma".fact_sensor_reading
SET isundermin = '1'
FROM "stage_aPharma".dim_sensors s
WHERE readingvalue < s.minValue;

--***************************       DDl; EDW                                *******************************


--Create dw for Dim_Room
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_rooms (
 R_ID SERIAL PRIMARY KEY,
 RoomId VARCHAR(16)
);

--Create dw for Dim_Sensor
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_sensors (
 S_ID SERIAL PRIMARY KEY,
 SensorId INT,
 sensorType INT,
 minValue INT,
 maxValue INT
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

--drop table dw_dim_time;
--Create dw for Dim_Time
CREATE TABLE IF NOT EXISTS "DW_aPHarma".dim_time (
 T_ID INT NOT NULL PRIMARY KEY,
 Time TIME,
 Second INT,
 Minute INT,
 Hour INT
);

--drop table dw_fact_sensor_reading;
--Create dw for Fact_SensorReading
CREATE TABLE IF NOT EXISTS "DW_aPHarma".fact_sensor_reading (
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
CREATE TEMP TABLE genTime
(
    Time Time
);

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
       (SELECT to_char((Time), 'HH24MISS'))::integer,
       Time,
       EXTRACT(Second FROM Time),
       EXTRACT(Minute FROM Time),
       EXTRACT(Hour FROM Time)
FROM genTime;

--Inserting the rooms
INSERT INTO "DW_aPHarma".dim_rooms(roomid)
SELECT roomid FROM "stage_aPharma".dim_rooms;

--Inserting the sensors
INSERT INTO "DW_aPHarma".dim_sensors( sensorid, sensortype, minvalue, maxvalue)
SELECT sensorid,
       sensortype,
       minvalue,
       maxvalue
           FROM "stage_aPharma".dim_sensors;

--DROP TEMP TABLE
DROP TABLE genDate;
DROP TABLE genTime;
--***************************       INCREMENTAL LOAD / SCHEDULING           *******************************

--***************************       TESTING                                 *******************************