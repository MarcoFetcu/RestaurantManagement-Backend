package com.example.restaurantApp.repository;

import com.example.restaurantApp.model.Meal;
import com.example.restaurantApp.model.OrderDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {
    //@Transactional
   // void deleteOrderDetailsById_order(@PathVariable Integer id_user);
//    Optional<OrderDetails> findOrderDetailsById_order(Integer id);
//    Optional<OrderDetails> findOrderDetailsById_order(Integer id);
//    void deleteAllById_order( Integer id);
}
