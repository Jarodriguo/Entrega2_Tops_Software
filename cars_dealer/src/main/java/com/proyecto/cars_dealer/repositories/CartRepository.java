package com.proyecto.cars_dealer.repositories;

import com.proyecto.cars_dealer.models.Cart;
import com.proyecto.cars_dealer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
