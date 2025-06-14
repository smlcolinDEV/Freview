package com.example.freview.controllers;

import com.example.freview.models.Role;;
import com.example.freview.repositories.UserRepository;
import com.example.freview.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public List<Role> role(){
        return roleService.findAll();
    }
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }


}
