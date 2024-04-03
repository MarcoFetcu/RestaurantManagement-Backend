package com.example.restaurantApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_meal;
    @Setter
    @Column(name="name")
    private String name;
    @Setter
    @Column(name="price")
    private Integer price;
    @Setter
    @Column(name="stock")
    private Integer stock;
}
