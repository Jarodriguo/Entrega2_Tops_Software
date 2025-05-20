package com.proyecto.cars_dealer.controllers;

import com.proyecto.cars_dealer.models.Car;
import com.proyecto.cars_dealer.models.Review;
import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.services.CarService;
import com.proyecto.cars_dealer.services.ReviewService;
import com.proyecto.cars_dealer.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    // Mostrar formulario para crear una review para un carro específico
    @GetMapping("/car/{carId}/new")
    public String showReviewForm(@PathVariable Long carId, Model model) {
        Car car = carService.findCarById(carId).orElseThrow(() -> new RuntimeException("Carro no encontrado con ID: " + carId));
        if (car == null) {
            return "redirect:/cars/list"; // o página de error
        }

        Review review = new Review();
        review.setCar(car); // asociar el carro al objeto Review

        model.addAttribute("review", review);
        return "reviews/form";
    }

    // Guardar una review
    @PostMapping("/save")
    public String saveReview(
            @Valid @ModelAttribute("review") Review review,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (result.hasErrors()) {
            return "reviews/form";
        }

        // Obtener usuario autenticado
        User user = userService.findByUsername(userDetails.getUsername());

        // Establecer relaciones antes de guardar
        review.setUser(user);

        reviewService.saveReview(review);
        return "redirect:/cars/" + review.getCar().getId(); // redirigir al detalle del carro
    }

    // Mostrar lista de reviews (opcional)
    @GetMapping("/list")
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews/list";
    }

    // Eliminar una review (solo autor o admin debería poder)
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Review review = reviewService.getReviewById(id).orElse(null);
        if (review == null) {
            return "redirect:/reviews/list";
        }

        // Validación para que solo el autor o admin pueda borrar
        User user = userService.findByUsername(userDetails.getUsername());
        if (review.getUser().getId().equals(user.getId()) || user.getRole().equals("ADMIN")) {
            reviewService.deleteReview(id);
        }

        return "redirect:/cars/" + review.getCar().getId();
    }

}
