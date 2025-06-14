package com.example.freview.services;

import com.example.freview.models.Role;
import com.example.freview.models.User;
import com.example.freview.repositories.RoleRepository;
import com.example.freview.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service

public class RoleService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    @Transactional
    public Role save(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);

        // Retrieve the user and role objects from the repository
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);

        // Add the role to the user's role collection
        user.getRoles().add(role);

        // Save the user to persist the changes
        userRepository.save(user);
    }
    @Transactional
    public Role update(Long id, Role updatedRole) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(updatedRole.getName()); // adapte selon les attributs de Role
                    return roleRepository.save(existingRole);
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }
    @Transactional
    public void delete(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
