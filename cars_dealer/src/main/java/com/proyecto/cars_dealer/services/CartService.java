package com.proyecto.cars_dealer.services;

import com.proyecto.cars_dealer.models.Car;
import com.proyecto.cars_dealer.models.Cart;
import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.repositories.CarRepository;
import com.proyecto.cars_dealer.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CarRepository carRepository;

    public Cart getOrCreateCart(User user){
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    public void addCarToCart(User user, Long carId){
        Cart cart = getOrCreateCart(user);
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Carro no encontrado"));

        if (!cart.getCars().contains(car)){
            cart.getCars().add(car);
            cartRepository.save(cart);
        }
    }

    public void removeCarfromCart(User user, Long carId){
        Cart cart = getOrCreateCart(user);
        cart.getCars().removeIf(car -> car.getId().equals(carId));
        cartRepository.save(cart);
    }

    public void clearCart(User user){
        Cart cart = getOrCreateCart(user);
        cart.getCars().clear();
        cartRepository.save(cart);
    }

    public Cart getCart(User user){
        return getOrCreateCart(user);
    }

}
