--***************************       TESTING                                 *******************************
--Comparing number of entries in the src db and in the stage for each dimension and the fact


--Testing for dim_rooms count, comparing number of entries in the src db and in the stage
SELECT count(*) AS count_on_source_rooms
    FROM "public".rooms;

SELECT count(*) AS count_on_stage_dim_rooms
    FROM "stage_aPharma".dim_rooms;

SELECT count(*) AS count_on_DW_dim_rooms
    FROM "DW_aPHarma".dim_rooms;


--Testing for dim_sensors count
SELECT count(*) AS count_on_source_sensors
    FROM "public".sensors;

SELECT count(*) AS count_on_stage_dim_sensors
    FROM "stage_aPharma".dim_sensors;

SELECT count(*) AS count_on_DW_dim_sensors
    FROM "DW_aPHarma".dim_sensors;


--Testing for fact count
SELECT count(*) AS count_on_source_Readings
    FROM "public".readings;

SELECT count(*) AS count_on_stage_fact_reading
    FROM "stage_aPharma".fact_sensor_reading;

SELECT count(*) AS count_on_DW_fact_reading
    FROM "DW_aPHarma".fact_sensor_reading;