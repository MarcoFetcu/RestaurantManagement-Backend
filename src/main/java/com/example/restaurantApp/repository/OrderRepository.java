package com.example.restaurantApp.repository;

import com.example.restaurantApp.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Orders findOrdersByDate(Date date);
}
