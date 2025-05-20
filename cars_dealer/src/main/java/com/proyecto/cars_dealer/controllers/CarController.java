package com.proyecto.cars_dealer.controllers;

import com.proyecto.cars_dealer.models.Car;
import com.proyecto.cars_dealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carService.findAllCars());
        return "cars/list";
    }

    @GetMapping("/{id}")
    public String carDetails(@PathVariable Long id, Model model) {
        Car car = carService.findCarById(id).orElseThrow(() -> new IllegalArgumentException("Carro no encontrado"));
        model.addAttribute("car", car);
        return "cars/detail";
    }

}
