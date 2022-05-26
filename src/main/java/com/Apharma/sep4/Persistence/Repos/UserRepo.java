package com.Apharma.sep4.Persistence.Repos;

import com.Apharma.sep4.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 Repository interface for querying the User entity table in the database.
 
 @author 4X Data team
 @version 1.0 - 28.04.2022
 */
public interface UserRepo extends JpaRepository<User, Integer>
{

}
