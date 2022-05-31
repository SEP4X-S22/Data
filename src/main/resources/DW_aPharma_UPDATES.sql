/*
Script to update the Data Warehouse
by inserting new values into the staging schema,
cleansing the data and adding the new values to the Data Warehouse

Written by the Data Team from Group 4X
*/

--Add cron scheduler
CREATE EXTENSION IF NOT EXISTS pg_cron;

--Create update function
CREATE OR REPLACE FUNCTION update_DW()

RETURNS VOID
AS
$$
BEGIN
--***************************       Insert new values into Stage                            *******************************

-- Room; Load to Stage
INSERT INTO "stage_apharma".dim_rooms
    (Room_Id)
    SELECT id
FROM public.rooms
WHERE id IN (SELECT id
FROM rooms EXCEPT SELECT room_id
FROM "stage_apharma".dim_rooms);

--Sensors; Load to Stage
INSERT INTO "stage_apharma".dim_sensors
    (Sensor_Id,
     sensor_Type,
     min_Value,
     max_Value)
    SELECT
            id,
            sensor_type,
            constraint_min_value,
            constraint_max_value
FROM public.sensors
WHERE id IN (SELECT id
FROM sensors EXCEPT SELECT sensor_id
FROM "stage_apharma".dim_sensors);


--Readings; Load to Stage
INSERT INTO "stage_apharma".fact_sensor_reading
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
INNER JOIN public.sensors s ON r.sensor_id = s.id
WHERE r.id IN (SELECT id
FROM readings EXCEPT SELECT reading_id
FROM "stage_apharma".fact_sensor_reading);

--***************************       Cleanse Data                            *******************************

-- set constraint if null for each sensorType
--Humidity (PERCENTAGE)
UPDATE "stage_apharma".dim_sensors
SET min_Value = 30
WHERE sensor_type = 0 AND min_Value = 0;

UPDATE "stage_apharma".dim_sensors
SET max_Value = 60
WHERE sensor_type = 0 AND max_Value = 0;

--CO2 (PPM)
UPDATE "stage_apharma".dim_sensors
SET min_Value = 250
WHERE sensor_type = 1 AND min_Value = 0;

UPDATE "stage_apharma".dim_sensors
SET max_Value = 1000
WHERE sensor_type = 1 AND max_Value = 0;

--Light (LUX)
UPDATE "stage_apharma".dim_sensors
SET min_Value = 300
WHERE sensor_type = 2 AND min_Value = 0;

UPDATE "stage_apharma".dim_sensors
SET max_Value = 500
WHERE sensor_type = 2 AND max_Value = 0;

--Temperature (CELSIUS)
UPDATE "stage_apharma".dim_sensors
SET min_Value = 15
WHERE sensor_type = 3 AND min_Value = 0;

UPDATE "stage_apharma".dim_sensors
SET max_Value = 30
WHERE sensor_type = 3 AND max_Value = 0;

-- set isOverMax and isUnderMin
UPDATE "stage_apharma".fact_sensor_reading
SET is_over_max = '0'
WHERE is_over_max IS  NULL;

UPDATE "stage_apharma".fact_sensor_reading
SET is_under_min = '0'
WHERE is_under_min IS NULL;

UPDATE "stage_apharma".fact_sensor_reading fsr
SET is_over_max = '1'
FROM "stage_apharma".dim_sensors s
WHERE reading_value > s.max_Value AND fsr.sensor_id = s.sensor_id;

UPDATE "stage_apharma".fact_sensor_reading fsr
SET is_under_min = '1'
FROM "stage_apharma".dim_sensors s
WHERE reading_value < s.min_Value  AND fsr.sensor_id = s.sensor_id;

--***************************       Insert new values into DataWarehouse                            *******************************


--Inserting the rooms
INSERT INTO "dw_apharma".dim_rooms
    (room_id)
SELECT
       room_id
FROM "stage_apharma".dim_rooms
WHERE room_id IN (SELECT room_id
FROM "stage_apharma".dim_rooms EXCEPT SELECT room_id
FROM "dw_apharma".dim_rooms);

--Inserting the sensors
INSERT INTO "dw_apharma".dim_sensors
    ( sensor_id,
     sensor_type,
     min_value,
     max_value)
SELECT
       sensor_id,
       sensor_type,
       min_value,
       max_value
FROM "stage_apharma".dim_sensors
WHERE sensor_id IN (SELECT sensor_id
FROM "stage_apharma".dim_sensors EXCEPT SELECT sensor_id
FROM "dw_apharma".dim_sensors);

--Inserting into Fact table
INSERT INTO "dw_apharma".fact_sensor_reading
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
FROM "stage_apharma".fact_sensor_reading fsr
JOIN "dw_apharma".dim_sensors ds on ds.sensor_id = fsr.sensor_id
JOIN "dw_apharma".dim_rooms dr on dr.room_id = fsr.room_id
WHERE fsr.reading_id IN (SELECT reading_id
FROM "stage_apharma".fact_sensor_reading EXCEPT SELECT reading_id
FROM "dw_apharma".fact_sensor_reading);

END;
$$ LANGUAGE plpgsql;

-- Schedule update for 01:00
SELECT cron.schedule('dw_apharma_UPDATES_@0100', '0 1 * * *', $$select update_DW();$$);
-- Schedule update for 07:00
SELECT cron.schedule('dw_apharma_UPDATES_@0700', '0 7 * * *', $$select update_DW();$$);
-- Schedule update for 13:00
SELECT cron.schedule('dw_apharma_UPDATES_@1300', '0 13 * * *', $$select update_DW();$$);
-- Schedule update for 20:00
SELECT cron.schedule('dw_apharma_UPDATES_@2000', '0 20 * * *', $$select update_DW();$$);
