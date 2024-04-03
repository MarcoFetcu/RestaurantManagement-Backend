package com.example.restaurantApp.controller;

import com.example.restaurantApp.model.Meal;
import com.example.restaurantApp.model.OrderDetails;
import com.example.restaurantApp.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderdetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private LoginController loginController=new LoginController();;


    @PostMapping("/create")
    private ResponseEntity<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails)
    {
        OrderDetails newOrderDetails= new OrderDetails(
          orderDetails.getId_details(),
          orderDetails.getId_order(),
          orderDetails.getId_meal(),
          orderDetails.getQuantity()
        );

        orderDetailsRepository.save(newOrderDetails);
        return new ResponseEntity<>(newOrderDetails, HttpStatus.CREATED);
    }

    private String createOrderDetailsFromOrder(OrderDetails orderDetails)
    {
        OrderDetails newOrderDetails= new OrderDetails(
                orderDetails.getId_details(),
                orderDetails.getId_order(),
                orderDetails.getId_meal(),
                orderDetails.getQuantity()
        );

        orderDetailsRepository.save(newOrderDetails);
        return "Created";
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<OrderDetails> updateOrderDetails(@PathVariable Integer id,@RequestBody OrderDetails orderDetails)
    {
        OrderDetails newOrderDetails=orderDetailsRepository.findById(id).orElse(null);
        newOrderDetails.setId_order(newOrderDetails.getId_order());
        newOrderDetails.setId_meal(newOrderDetails.getId_meal());
        newOrderDetails.setQuantity(newOrderDetails.getQuantity());

        orderDetailsRepository.save(newOrderDetails);

        return new ResponseEntity<>(newOrderDetails,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    private String deleteOrderDetails (@PathVariable Integer id)
    {
        orderDetailsRepository.deleteById(id);
        return "deleted";
    }

    @GetMapping("/read/{id}")
    private ResponseEntity<OrderDetails> readOrderDetails(@PathVariable Integer id){
        System.out.println("Succes");
        OrderDetails newOrderDetails = orderDetailsRepository.findById(id).orElse(null);
        return new ResponseEntity<>(newOrderDetails,HttpStatus.OK);
    }
}
