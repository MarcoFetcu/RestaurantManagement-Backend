package com.example.restaurantApp.controller;


import com.example.restaurantApp.model.Meal;
import com.example.restaurantApp.model.OrderDetails;
import com.example.restaurantApp.model.Orders;
import com.example.restaurantApp.model.User;
import com.example.restaurantApp.repository.MealRepository;
import com.example.restaurantApp.repository.OrderDetailsRepository;
import com.example.restaurantApp.repository.OrderRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping ("/order")
public class OrderController
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderDetailsController orderDetailsController;


    private LoginController loginController=new LoginController();



    @PostMapping("/create")
    private ResponseEntity<Orders> createOrder(@RequestBody Orders order)
    {
        Orders newOrder= new Orders(
                order.getId_order(),
                order.getId_user(),
                order.getDate(),
                order.getCost(),
                order.getStatuss()
        );
        orderRepository.save(newOrder);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders order)
    {
        Orders newOrder=orderRepository.findById(id).orElse(null);
        newOrder.setId_user(order.getId_user());
        newOrder.setCost(order.getCost());
        newOrder.setDate(order.getDate());
        newOrder.setStatuss(order.getStatuss());
        orderRepository.save(newOrder);
        return new ResponseEntity<>(newOrder, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/delete/{id}")
    private String deleteUser (@PathVariable Integer id)
    {
        try {

            Optional<Orders> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isPresent()) {
                Iterable<OrderDetails> orderDetails = orderDetailsRepository.findAll();
                Iterator<OrderDetails> iterator = orderDetails.iterator();
                while(iterator.hasNext()){
                    OrderDetails temp = iterator.next();
                    if(temp.getId_order() == id){
                        orderDetailsRepository.deleteById(temp.getId_details());
                    }
                }
                orderRepository.deleteById(id);
                return ("Order with ID " + id + " and its associated details have been deleted");
            } else {
                return ("Order with ID " + id + " not found");
            }
        } catch (Exception e) {
            // Handle other potential exceptions
            return "An error occurred while deleting the order: " + e.getMessage();
        }
    }

    @GetMapping("/read/{id}")
    private ResponseEntity<Orders> readMeal(@PathVariable Integer id){
        System.out.println("Succes");
        Orders newOrder = orderRepository.findById(id).orElse(null);
        return new ResponseEntity<>(newOrder,HttpStatus.OK);
    }

    @PutMapping("/makeorder")
    private ResponseEntity<String> readMeal(@RequestParam Map<String, String> orderDetails){

        StringBuilder response = new StringBuilder("Order received:\n");
        int cost=0;

        List<OrderDetails> details= new ArrayList<>();
        Date myDate= new Date(System.currentTimeMillis());
        User myUser=loginController.getLoggedAs();


        System.out.println(myUser.getId_user());
        System.out.println(myUser.toString());

        //Orders newOrder= new Orders(88,myUser.getId_user(),myDate,cost, "new" );
        Orders newOrder= new Orders(88,myUser.getId_user(),myDate,cost, "new" );
        orderRepository.save(newOrder);


        for (Map.Entry<String, String> entry : orderDetails.entrySet())
        {
            String productName = entry.getKey();
            String quantity = entry.getValue();
            Meal myMeal=mealRepository.findMealByName(productName);
            int price=myMeal.getPrice();

            if(Integer.valueOf(quantity)>myMeal.getStock())
                return new ResponseEntity<>("Insufficient stock", HttpStatus.INSUFFICIENT_STORAGE);

            //OrderDetails newOrderDetails=new OrderDetails(99,newOrder.getId_order(),myMeal.getId_meal(),Integer.valueOf(quantity));
            details.add(new OrderDetails(99,orderRepository.findOrdersByDate(myDate).getId_order(),myMeal.getId_meal(),Integer.valueOf(quantity)));
            cost+=price * Integer.parseInt(quantity);

            //orderDetailsRepository.save(newOrderDetails);
            response.append("Product: ").append(productName).append(", Quantity: ").append(quantity).append("\n");
        }

        newOrder.setCost(cost);
        orderRepository.save(newOrder);

        for(OrderDetails detail : details)
        {
            System.out.println(detail.toString());
           Meal myMeal= mealRepository.findById(detail.getId_meal()).orElse(null);
           if(myMeal==null)
               return new ResponseEntity<>("Err", HttpStatus.BAD_REQUEST);
           myMeal.setStock(myMeal.getStock()-detail.getQuantity());
           mealRepository.save(myMeal);
           orderDetailsRepository.save(detail);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }

    @PutMapping("/updatestatus/{id}/{status}")
    private ResponseEntity<String> readMeal(@PathVariable Integer id,@PathVariable String status){

        if(!loginController.isLoggedIn() || (loginController.isLoggedInAsAdmin() && !loginController.getLoggedAs().getUsername().equals("marcofd") ))
            return new ResponseEntity<>("Not logged in as user",HttpStatus.BAD_REQUEST);


        Orders newOrder = orderRepository.findById(id).orElse(null);
        if(newOrder==null)
            return new ResponseEntity<>("Order could not be found",HttpStatus.BAD_REQUEST);

        newOrder.setStatuss(status);
        orderRepository.save(newOrder);


        return new ResponseEntity<>("Status updated",HttpStatus.OK);
    }


}
