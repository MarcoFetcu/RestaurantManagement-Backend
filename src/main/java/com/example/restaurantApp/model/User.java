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
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;
    @Setter
    @Column(name="name")
    private String name;
    @Setter
    @Column(name="type")
    private String type;
    @Setter
    @Column(name="username")
    private String username;
    @Setter
    @Column(name="password")
    private String password;
}
