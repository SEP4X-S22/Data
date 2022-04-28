package WebAPI.Repos;

import Model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepo extends JpaRepository<Reading, Integer>
{
}
