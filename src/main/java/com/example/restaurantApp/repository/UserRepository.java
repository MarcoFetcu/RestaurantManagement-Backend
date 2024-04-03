package com.example.restaurantApp.repository;

import com.example.restaurantApp.model.Meal;
import com.example.restaurantApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(@RequestParam String username);
}
