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
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseHandler
{
  private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Bean CommandLineRunner initDatabase(ReadingRepo readingRepo, UserRepo userRepo)
  {

    long now = new Date().getTime();
    long delay = 5 * 60 * 1000;

    return args -> {
      //dummy data
      for (int i = 0; i < 1000; i++)
      {
        log.info("Preloading " + readingRepo.save(
            new Reading(1, Reading.SensorType.CO2,
                (int) (Math.random() * (50 - 1 + 1) + 1),
                new Date(now + i * delay))));
        log.info("Preloading " + readingRepo.save(
            new Reading(1, Reading.SensorType.Humidity,
                (int) (Math.random() * (50 - 1 + 1) + 1),
                new Date(now + i * delay))));
        log.info("Preloading " + readingRepo.save(
            new Reading(1, Reading.SensorType.Light,
                (int) (Math.random() * (50 - 1 + 1) + 1),
                new Date(now + i * delay))));
        log.info("Preloading " + readingRepo.save(
            new Reading(1, Reading.SensorType.Temperature,
                (int) (Math.random() * (50 - 1 + 1) + 1),
                new Date(now + i * delay))));
      }
    };
  }
}
