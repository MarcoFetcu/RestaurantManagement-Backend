package com.example.restaurantApp.controller;

import com.example.restaurantApp.model.Meal;
import com.example.restaurantApp.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Autowired
    private MealRepository mealRepository;
    private LoginController loginController=new LoginController();;

    @PostMapping("/create")
    private ResponseEntity<Meal> createMeal(@RequestBody Meal meal)
    {
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Meal newMeal= new Meal(
                meal.getId_meal(),
                meal.getName(),
                meal.getPrice(),
                meal.getStock()
        );
        mealRepository.save(newMeal);
        return new ResponseEntity<>(newMeal, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Meal> updateMeal(@PathVariable Integer id, @RequestBody Meal meal)
    {
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Meal newMeal=mealRepository.findById(id).orElse(null);
        if(newMeal==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        newMeal.setName(meal.getName());
        newMeal.setStock(meal.getStock());
        newMeal.setPrice(meal.getPrice());
        mealRepository.save(newMeal);
        return new ResponseEntity<>(newMeal, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    private String deleteMeal (@PathVariable Integer id)
    {
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return "You are not logged in, or you are not admin";
        if(mealRepository.findById(id).orElse(null)==null)
            return "Meal you are trying to delete does not exist";
        mealRepository.deleteById(id);

        return "deleted";
    }

    @GetMapping("/read/{id}")
    private ResponseEntity<Meal> readMeal(@PathVariable Integer id){
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(mealRepository.findById(id).orElse(null)==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Meal newMeal = mealRepository.findById(id).orElse(null);
        return new ResponseEntity<>(newMeal,HttpStatus.OK);
    }

    @PutMapping("/updatestock/{id}/{nr}")
    private ResponseEntity<Meal> readMeal(@PathVariable Integer id, @PathVariable Integer nr){
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(mealRepository.findById(id).orElse(null)==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Meal newMeal = mealRepository.findById(id).orElse(null);
        newMeal.setStock(nr);
        mealRepository.save(newMeal);
        return new ResponseEntity<>(newMeal,HttpStatus.OK);

    }

}
