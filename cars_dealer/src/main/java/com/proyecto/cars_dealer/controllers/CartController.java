package com.proyecto.cars_dealer.controllers;

import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.models.Cart;
import com.proyecto.cars_dealer.services.CartService;
import com.proyecto.cars_dealer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Mostrar el carrito del usuario autenticado
    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Cart cart = cartService.getCart(user);
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    // Agregar un carro al carrito
    @PostMapping("/add/{carId}")
    public String addCarToCart(@PathVariable Long carId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        cartService.addCarToCart(user, carId);
        return "redirect:/cart";
    }

    // Eliminar un carro del carrito
    @PostMapping("/remove/{carId}")
    public String removeCarFromCart(@PathVariable Long carId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        cartService.removeCarfromCart(user, carId);
        return "redirect:/cart";
    }

    // Vaciar todo el carrito
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        cartService.clearCart(user);
        return "redirect:/cart";
    }

}
