package com.example.freview.services;

import com.example.freview.dto.UpdatedPasswordRequestDTO;
import com.example.freview.exceptions.ResourceNotFoundException;
import com.example.freview.models.User;
import com.example.freview.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), authorities);
        }
    }

    public User userSave(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User update(Long id, User user) {
        var existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setEmail(user.getEmail());
            return userRepository.save(updatedUser);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    public User updateUserPassword(UpdatedPasswordRequestDTO request) {
        var userToUpdate = findById(request.getId());
        if (userToUpdate != null) {
            userToUpdate.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(userToUpdate);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + request.getId());
        }
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
