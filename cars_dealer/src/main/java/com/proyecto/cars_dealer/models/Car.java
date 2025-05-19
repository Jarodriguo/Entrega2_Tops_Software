package com.proyecto.cars_dealer.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cars")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long model;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String carType;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private double price;
}