package com.proyecto.cars_dealer.services;

import com.proyecto.cars_dealer.models.Review;
import com.proyecto.cars_dealer.repositories.ReviewRepository;
import com.proyecto.cars_dealer.repositories.CarRepository;
import com.proyecto.cars_dealer.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener todas las reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Obtener una review por ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // Guardar una review (con relaciones)
    public Review saveReview(Review review) {
        if (review.getUser() == null || review.getCar() == null) {
            throw new IllegalArgumentException("La review debe tener un usuario y un carro asignado.");
        }

        // Validar existencia de usuario y carro
        if (!userRepository.existsById(review.getUser().getId())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + review.getUser().getId());
        }

        if (!carRepository.existsById(review.getCar().getId())) {
            throw new RuntimeException("Carro no encontrado con ID: " + review.getCar().getId());
        }

        return reviewRepository.save(review);
    }

    // Eliminar una review
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la review con ID: " + id);
        }
        reviewRepository.deleteById(id);
    }

    // Verificar si una review existe
    public boolean existsReview(Long id) {
        return reviewRepository.existsById(id);
    }

    // Actualizar review
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la review con ID: " + id));

        review.setTitle(reviewDetails.getTitle());
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());

        // No cambiamos el usuario ni el carro en la actualización por seguridad
        return reviewRepository.save(review);
    }

}
