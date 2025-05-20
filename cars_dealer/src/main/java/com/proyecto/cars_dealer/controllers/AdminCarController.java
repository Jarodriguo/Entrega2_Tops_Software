package com.proyecto.cars_dealer.controllers;

import com.proyecto.cars_dealer.models.Car;
import com.proyecto.cars_dealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    @Autowired
    private CarService carService;

    // Mostrar formulario para crear carro
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("car", new Car());
        return "admin/cars/form";
    }

    // Guardar carro nuevo
    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute Car car, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "admin/cars/form";
        }
        carService.saveCar(car);
        return "redirect:/admin/cars/list";
    }

    // Mostrar todos los carros
    @GetMapping("/list")
    public String listCars(Model model) {
        model.addAttribute("cars", carService.findAllCars());
        return "admin/cars/list";
    }

    // Eliminar carro por ID
    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCarById(id);
        return "redirect:/admin/cars/list";
    }
}
