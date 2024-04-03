package com.example.restaurantApp.repository;

import com.example.restaurantApp.model.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface MealRepository extends CrudRepository<Meal,Integer> {

    //Meal findMealById_meal(@PathVariable Integer id_meal);
    Meal findMealByName(String name);
}
