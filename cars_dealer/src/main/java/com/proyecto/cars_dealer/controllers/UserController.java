package com.proyecto.cars_dealer.controllers;

import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Procesar registro
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) { // El @ModelAttribute lo que hace es que recibe los datos del formulario y los convierte en un objeto User
        if (userService.emailExists(user.getEmail()) || userService.usernameExists(user.getUsername())){
            model.addAttribute("error", "El correo o usuario ya están registrados");
            return "auth/register";
        }

        userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        return "redirect:/login?success";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

}
