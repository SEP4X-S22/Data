package com.Apharma.sep4.WebAPI.Repos;

import com.Apharma.sep4.Persistence.DTO.RoomDTO;

import com.Apharma.sep4.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, String>
{
  @Query("SELECT new com.Apharma.sep4.Persistence.DTO.RoomDTO(r.id, COUNT(s.id))"
      + " FROM Room r"
      + " JOIN r.sensors s"
      + " GROUP BY r.id")
  List<RoomDTO> getAllRooms();
}

