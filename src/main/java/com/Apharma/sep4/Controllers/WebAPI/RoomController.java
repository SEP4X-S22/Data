package com.Apharma.sep4.Controllers.WebAPI;

import com.Apharma.sep4.Persistence.DTO.RoomDTO;
import com.Apharma.sep4.Persistence.Repos.RoomRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 Controller class for handling requests to the API that deal with Rooms.
 
 @author 4X Data team
 @version 2.1 - 26.05.2022
 @implNote Extracted methods irrelevant to Rooms. - Aldís Eir Hansen & Alexandru Malai
 */
@RestController
public class RoomController
{
	private final RoomRepo roomRepo;
  
  /**
   One argument constructor for a RoomController.
   
   @param roomRepo RoomRepo object to access the database
   */
	public RoomController(RoomRepo roomRepo)
	{
		this.roomRepo = roomRepo;
	}
  
  /**
   Method exposing the default endpoint for getting a greeting from the Data team.
   
   @return String of Data team greeting
   */
	@GetMapping("/")
	private String getAll()
	{
		return "Hello from the Data Team!";
	}
  
  /**
   Method exposing the endpoint for getting all RoomDTO objects.
   
   @return List of RoomDTO objects from the RoomRepo
   */
	@GetMapping("/rooms")
	private List<RoomDTO> getAllRooms()
	{
		return roomRepo.getAllRooms();
	}
}