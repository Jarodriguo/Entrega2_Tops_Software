package com.proyecto.cars_dealer.repositories;

import com.proyecto.cars_dealer.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByModel(String model);

    Optional<Car> findByPrice(double price);
}
