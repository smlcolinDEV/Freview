package com.example.freview.demo;

import com.example.freview.enums.WatchedStatus;
import com.example.freview.models.*;
import com.example.freview.repositories.ReviewRepository;
import com.example.freview.repositories.SharedCollectionRepository;
import com.example.freview.repositories.UserRepository;
import com.example.freview.repositories.MediaRepostiory;

import com.example.freview.services.SharedCollectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepostiory mediaRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SharedCollectionRepository sharedCollectionRepository;

    @Autowired
    private SharedCollectionService sharedCollectionService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create and save users
        User user1 = new User("smlco", "Media", "smlco@gmail.com");
        User user2 = new User("salva", "Salva", "salva@gmail.com");
        User user3 = new User("Jinx", "Arcane", "Vander@gmail.com");
        userRepository.saveAll(List.of(user1, user2, user3));

        // Create and save media
        Movie movie1 = new Movie("Pulp Fiction", "Tarantino's movie", 154);
        Movie movie2 = new Movie("Forrest Gump", "Tom Hanks's movie", 154);
        Serie serie1 = new Serie("Breaking Bad", "Drug Dealer", 5);
        Serie serie2 = new Serie("HIMYM", "How he met their mother", 9);
        mediaRepository.saveAll(List.of(movie1, movie2, serie1, serie2));


        // Create and save shared lists
        SharedCollection sharedCollection = new SharedCollection();
        sharedCollection.setTitle("Favorite Media");
        sharedCollectionRepository.saveAll(List.of(sharedCollection));
        sharedCollectionService.addMediaToSharedList(sharedCollection.getId(),movie1);
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(),user1);
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(),user2);
        sharedCollectionService.addUserToSharedList(sharedCollection.getId(),user3);




    }
}

