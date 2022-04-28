package WebAPI.Controllers;

import Model.Reading;
import WebAPI.Repos.ReadingRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReadingController
{
	private final ReadingRepo readingRepo;
	
	public ReadingController(ReadingRepo readingRepo)
	{
		this.readingRepo = readingRepo;
	}
	
	@GetMapping("/room/reading")
	private List<Reading> getAll()
	{
		return readingRepo.findAll();
	}
	
	@GetMapping("/{room}/{sensorType}")
	private List<Reading> getSensorReading(@PathVariable int room, @PathVariable Reading.SensorType sensorType)
	{
		return readingRepo.findByRoom(room).stream().filter((reading) -> reading.getSensorType() == sensorType).toList();
	}
}
