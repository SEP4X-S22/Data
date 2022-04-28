package com.Apharma.sep4.WebAPI.Repos;


import com.Apharma.sep4.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>
{
}
