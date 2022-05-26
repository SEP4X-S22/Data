package com.Apharma.sep4.Persistence.Repos;

import com.Apharma.sep4.Persistence.DTO.RoomDTO;

import com.Apharma.sep4.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 Repository interface for querying the Room entity table in the database.
 
 @author 4X Data team
 @version 2.0 - 18.05.2022
 @implNote Modified query to include number of Sensors in the Rooms. - Aldís Eir Hansen & Claudiu Cordunianu
 */
@Repository
public interface RoomRepo extends JpaRepository<Room, String>
{
  /**
   Abstract method for querying the database for all Rooms.
   
   @return List of Rooms represented by RoomDTO objects
   */
  @Query("SELECT new com.Apharma.sep4.Persistence.DTO.RoomDTO(r.id, COUNT(s.id))"
      + " FROM Room r"
      + " JOIN r.sensors s"
      + " GROUP BY r.id")
  List<RoomDTO> getAllRooms();
}

