package com.example.freview.demo;

import com.example.freview.models.*;
import com.example.freview.repositories.*;
import com.example.freview.services.RoleService;
import com.example.freview.services.SharedCollectionService;
import com.example.freview.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MediaRepostiory mediaRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SharedCollectionRepository sharedCollectionRepository;

    @Autowired
    private SharedCollectionService sharedCollectionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        roleRepository.saveAll(List.of(role1, role2));

        // Create and save users
        User user1 = new User("smlco", "media", "smlco@gmail.com");
        User user2 = new User("salva", "Salva", "salva@gmail.com");
        User user3 = new User("Jinx", "Arcane", "ishaowjinx@gmail.com");

        // Assurez-vous que les utilisateurs existent avant d'ajouter des rôles
        userService.userSave(user1);
        userService.userSave(user2);
        userService.userSave(user3);

        // Ajoutez des rôles aux utilisateurs
        roleService.addRoleToUser("smlco", "ROLE_USER");
        roleService.addRoleToUser("Jinx", "ROLE_USER");
        roleService.addRoleToUser("salva", "ROLE_ADMIN");

        // Create and save media
        Movie movie1 = new Movie("Pulp Fiction", "Tarantino's movie", 154);
        Movie movie2 = new Movie("Forrest Gump", "Tom Hanks's movie", 154);
        Serie serie1 = new Serie("Breaking Bad", "Drug Dealer", 5);
        Serie serie2 = new Serie("HIMYM", "How he met their mother", 9);
        mediaRepository.saveAll(List.of(movie1, movie2, serie1, serie2));

        // Create and save shared lists
        SharedCollection sharedCollection = new SharedCollection();
        sharedCollection.setName("Favorite Media");
        SharedCollection sharedCollection2 = new SharedCollection();
        sharedCollection2.setName("Joiining Collection");
        sharedCollectionRepository.saveAll(List.of(sharedCollection, sharedCollection2));

        sharedCollectionService.addMediaToSharedList(sharedCollection.getId(), movie1);
        sharedCollectionService.addUserToSharedList(sharedCollection2.getId(), user3.getId());
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(), user1.getId());
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(), user2.getId());
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(), user3.getId());
    }
}
