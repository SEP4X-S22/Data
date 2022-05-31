/*
Script to test the Data Warehouse
by comparing number of entries in the src db,
the stage and the Data Warehouse for each dimension and the fact


Written by the Data Team from Group 4X
*/

--***************************       TESTING                                 *******************************


--Testing for dim_rooms count, comparing number of entries in the src db and in the stage
SELECT count(*) AS count_on_source_rooms
    FROM "public".rooms;

SELECT count(*) AS count_on_stage_dim_rooms
    FROM "stage_apharma".dim_rooms;

SELECT count(*) AS count_on_DW_dim_rooms
    FROM "dw_apharma".dim_rooms;


--Testing for dim_sensors count
SELECT count(*) AS count_on_source_sensors
    FROM "public".sensors;

SELECT count(*) AS count_on_stage_dim_sensors
    FROM "stage_apharma".dim_sensors;

SELECT count(*) AS count_on_DW_dim_sensors
    FROM "dw_apharma".dim_sensors;


--Testing for fact count
SELECT count(*) AS count_on_source_Readings
    FROM "public".readings;

SELECT count(*) AS count_on_stage_fact_reading
    FROM "stage_apharma".fact_sensor_reading;

SELECT count(*) AS count_on_DW_fact_reading
    FROM "dw_apharma".fact_sensor_reading;