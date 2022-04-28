package com.Apharma.sep4.DAO;

import com.Apharma.sep4.Model.Reading;

import com.Apharma.sep4.Model.User;
import com.Apharma.sep4.WebAPI.Repos.ReadingRepo;

import com.Apharma.sep4.WebAPI.Repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseHandler
{
  private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Bean CommandLineRunner initDatabase(ReadingRepo readingRepo, UserRepo userRepo)
  {
    // bloat
    long now = new Date().getTime();
    long delay = 5*60*1000;

    return args -> {
      //dummy data
      log.info("Preloading " + readingRepo.save(
          new Reading(1, Reading.SensorType.CO2, 40, new Date(now + delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Humidity, 50, new Date(now +2*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Light, 1000, new Date(now +3*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Temperature, 5, new Date(now +4*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(1,Reading.SensorType.Temperature, 10, new Date(now +5*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(2,Reading.SensorType.Light, 100000000, new Date(now +6*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(2,Reading.SensorType.Temperature, 20, new Date(now +7*delay))));
      log.info("Preloading " + readingRepo.save(
          new Reading(3,Reading.SensorType.Temperature, 33, new Date(now +8*delay))));

      log.info("Preloading " + userRepo.save(new User(1, "Frodo Baggins", true)));
    };
  }
}
