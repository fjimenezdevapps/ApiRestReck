package com.fjdev.rackapirest.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fjdev.rackapirest.model.User;
import com.fjdev.rackapirest.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Obtener usuario por nameC
    public Optional<User> getUserByNameC(String nameC) {
        return userRepository.findByNameC(nameC);
    }

    // Guardar un nuevo usuario (con encriptación de contraseña)
    public User saveUser(User user) {
        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Actualizar datos del usuario
    public Optional<User> updateUser(Long id, User newUserData) {
        return userRepository.findById(id).map(user -> {
            user.setNameC(newUserData.getNameC()); // Actualiza nameC
            user.setEmail(newUserData.getEmail()); // Actualiza email
            // Si hay un nuevo password, lo actualizamos y lo encriptamos
            if (newUserData.getPassword() != null && !newUserData.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(newUserData.getPassword()));
            }
            return userRepository.save(user);
        });
    }

    // Eliminar usuario por ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Verificar credenciales (autenticación)
    public boolean verifyCredentials(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Compara la contraseña cruda con la contraseña encriptada
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}