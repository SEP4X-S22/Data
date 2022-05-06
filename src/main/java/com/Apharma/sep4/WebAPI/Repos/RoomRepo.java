package com.Apharma.sep4.WebAPI.Repos;


import com.Apharma.sep4.DTO.ReadingDTO;
import com.Apharma.sep4.DTO.RoomDTO;
import com.Apharma.sep4.DTO.SensorDTO;
import com.Apharma.sep4.Model.Reading;

import com.Apharma.sep4.Model.Room;
import com.Apharma.sep4.Model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, String>
{
  @Query("SELECT new com.Apharma.sep4.DTO.RoomDTO(id) FROM Room")
  List<RoomDTO> getAllRooms();
}
