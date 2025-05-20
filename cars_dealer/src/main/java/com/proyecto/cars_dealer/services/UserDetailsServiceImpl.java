package com.proyecto.cars_dealer.services;

import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Spring usará esto para cargar usuarios al iniciar sesión
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(usernameOrEmail)
                .orElse(userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")));
        
        // Crear la autoridad con el rol del usuario (por ejemplo, ROLE_ADMIN o ROLE_USER)
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
);
}
}
