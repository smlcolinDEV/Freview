package com.example.freview.controllers;


import com.example.freview.dto.UpdatedPasswordRequestDTO;
import com.example.freview.models.User;
import com.example.freview.repositories.UserRepository;
import com.example.freview.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
        return userService.save(user);
    }
    @PutMapping("/id")
    public User modifyUser(@PathVariable Long id, @RequestBody User user){
        return userService.update(id, user);
    }
    @PatchMapping("/id")
    public User patchUser(@RequestBody UpdatedPasswordRequestDTO request){
        return userService.updateUserPassword(request);
    }
    @DeleteMapping("/id")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

}
