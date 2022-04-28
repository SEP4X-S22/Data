package DAO;

import Model.Reading;
import Model.User;
import WebAPI.Repos.ReadingRepo;
import WebAPI.Repos.UserRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseHandler
{
  private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Bean CommandLineRunner initDatabase(ReadingRepo readingRepo, UserRepo userRepo)
  {
    return args -> {
      log.info("Preloading " + readingRepo.save(new Reading()));
      log.info("Preloading " + userRepo.save(new User(1, "Frodo Baggins", true)));
    };
  }
}
