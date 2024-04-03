package com.example.restaurantApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_details;
    @Setter
    @Column(name="id_order")
    private Integer id_order;
    @Setter
    @Column(name="id_meal")
    private Integer id_meal;
    @Setter
    @Column(name="quantity")
    private Integer quantity;
}
