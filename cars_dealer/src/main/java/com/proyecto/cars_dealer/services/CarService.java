package com.proyecto.cars_dealer.services;

import com.proyecto.cars_dealer.models.Car;
import com.proyecto.cars_dealer.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> findCarById(Long id){
        return carRepository.findById(id);
    }

    public Car saveCar(Car car){
        return carRepository.save(car);
    }
    
    public void deleteCarById(Long id){
        carRepository.deleteById(id);
    }

    public Optional<Car> findCarByModel(String model){
        return carRepository.findByModel(model);
    }

    public Optional<Car> findCarByPrice(double price){
        return carRepository.findByPrice(price);
    }

}
