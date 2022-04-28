package DAO;

import Model.Reading;
import Model.User;
import WebAPI.Repos.ReadingRepo;
import WebAPI.Repos.UserRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Configuration
public class DatabaseHandler
{
  private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Bean CommandLineRunner initDatabase(ReadingRepo readingRepo, UserRepo userRepo)
  {
    return args -> {
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.CO2, 40, LocalDateTime.now())));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Humidity, 50, LocalDateTime.now())));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Light, 1000, LocalDateTime.now())));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Temperature, 5, LocalDateTime.now())));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Temperature, 10, LocalDateTime.now())));

      log.info("Preloading " + userRepo.save(new User(1, "Frodo Baggins", true)));
    };
  }
}
