package WebAPI.Repos;

import Model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
  List<Reading> findBySensorType(Reading.SensorType senseMe);
}
