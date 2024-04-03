package com.example.restaurantApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_order;
    @Setter
    @Column(name="id_user")
    private Integer id_user;
    @Setter
    @Column(name="date")
    private Date date;
    @Setter
    @Column(name="cost")
    private Integer cost;
    @Setter
    @Column(name="statuss")
    private String statuss;

}
