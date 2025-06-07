package com.example.freview.services;

import com.example.freview.dto.UpdatedPasswordRequestDTO;
import com.example.freview.exceptions.ResourceNotFoundException;
import com.example.freview.models.User;
import com.example.freview.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User update(Long id,User user){
        var existingUser = userRepository.findById(user.getId());
        if(existingUser.isPresent()){
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());
            return userRepository.save(updatedUser);
        }
        else{
            throw new ResourceNotFoundException("Car not found with id: " + id);
        }
    }
    public User updateUserPassword(UpdatedPasswordRequestDTO request ){
        var userToUpdate = findById(request.getId());
        userToUpdate.setPassword(request.getPassword());
        return userRepository.save(userToUpdate);

    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
