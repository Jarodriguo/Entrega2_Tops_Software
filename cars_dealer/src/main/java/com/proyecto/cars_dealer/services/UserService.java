package com.proyecto.cars_dealer.services;

import com.proyecto.cars_dealer.models.User;
import com.proyecto.cars_dealer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Buscar usuarios por ID
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    // Guardar un nuevo usuario
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    // Verificar si el email ya está en uso
    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    // Verificar si el username ya está en uso
    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    // Buscar por email o username
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Registra un nuevo usuario y asigna el rol según su username. Si el username es admin, se le asigna un rol de ADMIN, de lo contrario USER
    public User registerUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Asignación de rol
        if (username.equalsIgnoreCase("admin")){
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

}