package com.example.freview.services;

import com.example.freview.dto.*;
import com.example.freview.models.*;
import com.example.freview.repositories.MediaRepostiory;
import com.example.freview.repositories.MovieRepository;
import com.example.freview.repositories.ReviewRepository;
import com.example.freview.repositories.SharedCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class SharedCollectionService {

    private final SharedCollectionRepository sharedCollectionRepository;
    private final ReviewService reviewService;
    private final MovieService movieService;
    private final SerieService serieService;
    private final MediaRepostiory mediaRepostiory;
    private final UserService userService;
    private final MovieRepository movieRepostiory;
    private ReviewRepository reviewRepository;

    public SharedCollection addSharedCollection(SharedCollection sharedCollection) {
        return sharedCollectionRepository.save(sharedCollection);
    }

    public List<SharedCollection> findAllSharedCollections() {
        return sharedCollectionRepository.findAll();
    }

    public SharedCollectionDTO findSharedCollectionById(Long id) {
        SharedCollection entity = sharedCollectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SharedCollection not found"));

        SharedCollectionDTO dto = new SharedCollectionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMediaList(
                entity.getMediaList().stream()
                        .map(media -> {
                            MediaDTO mediaDTO = new MediaDTO();
                            mediaDTO.setId(media.getId());
                            mediaDTO.setTitle(media.getTitle());
                            mediaDTO.setOverview(media.getOverview());
                            return mediaDTO;
                        })
                        .collect(Collectors.toList()));
        dto.setUsers(entity.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername())) // selon ton UserDTO
                .collect(Collectors.toList()));
        dto.setReviews(
                entity.getReviews().stream()
                        .map(review -> {
                            ReviewDTO reviewDTO = new ReviewDTO();
                            reviewDTO.setId(review.getId());
                            reviewDTO.setComment(review.getComment());
                            reviewDTO.setRating(review.getRating());
                            return reviewDTO;
                        })
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public List<SharedCollectionDTO> getSharedCollections() {
        return sharedCollectionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SharedCollectionDTO> getSharedCollectionsByUserId(Long userId) {
        return sharedCollectionRepository.findSharedCollectionsByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SharedCollectionDTO convertToDTO(SharedCollection sharedCollection) {
        List<MediaDTO> mediaDTOs = sharedCollection.getMediaList().stream()
                .map(media -> new MediaDTO(media.getId(), media.getTitle(), media.getOverview()))
                .collect(Collectors.toList());

        List<UserDTO> userDTOs = sharedCollection.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());

        return new SharedCollectionDTO(
                sharedCollection.getId(),
                sharedCollection.getName(),
                mediaDTOs,
                userDTOs
        );
    }

    @Transactional
    public void addMovieFromTmdbToSharedList(Long sharedListId, Long movieId) {

        MovieDTO movieDTO = movieService.fetchMovie(movieId);

        Movie movie = new Movie();

        movie.setTitle(movieDTO.getTitle());
        movie.setOverview(movieDTO.getOverview());
        movie.setRuntime(movieDTO.getRuntime());

        Movie savedMovie = movieRepostiory.save(movie);


        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new RuntimeException("SharedCollection not found"));

        sharedCollection.getMediaList().add(savedMovie);
        sharedCollectionRepository.save(sharedCollection);


        reviewService.createReviewsForMediaInSharedList(savedMovie, sharedCollection);
    }


    @Transactional
    public void addSerieFromTmdbToSharedList(Long sharedListId, Long serieId) {

        Serie toSavedSerie = this.serieService.fetchSerie(serieId);

        // Save the media object to the database
        Media savedMedia = mediaRepostiory.save(toSavedSerie);

        // Add the saved media to the shared list
        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new RuntimeException("SharedCollection not found"));
        sharedCollection.getMediaList().add(savedMedia);
        sharedCollectionRepository.save(sharedCollection);

        // Create reviews for the media in the shared list
        reviewService.createReviewsForMediaInSharedList(savedMedia, sharedCollection);
    }

    @Transactional
    public SharedCollection createSharedCollection(String title) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(username); // ou gestion null si nécessaire

        SharedCollection collection = new SharedCollection();
        collection.setName(title);
        collection.setMediaList(new ArrayList<>()); // vide
        collection.setUsers(new ArrayList<>());
        collection.getUsers().add(user);

        return sharedCollectionRepository.save(collection);
    }

    @Transactional
    public void joinSharedCollectionById(Long sharedListId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User attempting to join: {}", username);

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new EntityNotFoundException("SharedCollection non trouvée pour ID : " + sharedListId));

        if (sharedCollection.getUsers().contains(user)) {
            throw new IllegalStateException("Utilisateur déjà membre de cette SharedCollection.");
        }

        sharedCollection.getUsers().add(user);
        sharedCollectionRepository.save(sharedCollection);

        reviewService.createReviewsForUserInSharedList(user, sharedCollection);
    }

    @Transactional
    public void addMediaToSharedList(Long sharedListId, Media media) {
        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new RuntimeException("SharedCollection not found"));
        sharedCollection.getMediaList().add(media);
        sharedCollectionRepository.save(sharedCollection);
        reviewService.createReviewsForMediaInSharedList(media, sharedCollection);
    }

    @Transactional
    public void addUserToSharedList(Long sharedListId, Long userId) {
        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new RuntimeException("SharedCollection not found with ID: " + sharedListId));

        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        if (!sharedCollection.getUsers().contains(user)) {
            sharedCollection.getUsers().add(user);
            sharedCollectionRepository.save(sharedCollection);
        }

        reviewService.createReviewsForUserInSharedList(user, sharedCollection);
    }

    @Transactional
    public void updateReviewForMediaInSharedCollection(Long sharedCollectionId, Long mediaId, String newComment, Double newRating) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedCollectionId)
                .orElseThrow(() -> new EntityNotFoundException("SharedCollection not found"));

        Review review = reviewRepository.findByUserAndMediaAndSharedCollection(user.getId(), mediaId, sharedCollectionId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        // Met à jour la review
        review.setComment(newComment);
        review.setRating(newRating);

        reviewRepository.save(review);
    }

    public SharedCollectionDTO updateSharedCollection(Long id, SharedCollectionDTO dto) {
        SharedCollection collection = sharedCollectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SharedCollection not found"));

        // Mise à jour du nom uniquement (les listes sont gérées via d'autres endpoints)
        collection.setName(dto.getName());

        // Enregistrement en base
        sharedCollectionRepository.save(collection);

        // Création du DTO de retour (les listes ne sont pas recalculées ici, pour éviter la surcharge)
        SharedCollectionDTO updatedDTO = new SharedCollectionDTO();
        updatedDTO.setId(collection.getId());
        updatedDTO.setName(collection.getName());

        return updatedDTO;
    }


    public void delete(Long sharedListId) {
        sharedCollectionRepository.deleteById(sharedListId);
    }

}
