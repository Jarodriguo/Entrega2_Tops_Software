package com.proyecto.cars_dealer.repositories;

import com.proyecto.cars_dealer.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByModel(String model);

    Optional<Car> findByPrice(double price);
}
