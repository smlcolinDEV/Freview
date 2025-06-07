package com.example.freview.services;

import com.example.freview.dto.MediaDTO;
import com.example.freview.dto.SharedCollectionDTO;
import com.example.freview.dto.UserDTO;
import com.example.freview.models.Media;
import com.example.freview.models.SharedCollection;
import com.example.freview.models.User;
import com.example.freview.repositories.SharedCollectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SharedCollectionService {
    private final SharedCollectionRepository sharedCollectionRepository;
    private final ReviewService reviewService;

    public SharedCollectionService(SharedCollectionRepository sharedCollectionRepository, ReviewService reviewService) {
        this.sharedCollectionRepository = sharedCollectionRepository;
        this.reviewService = reviewService;
    }

    public List<SharedCollection> findAllSharedCollections(){
        return sharedCollectionRepository.findAll();
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
                .map(media -> new MediaDTO(media.getId(), media.getTitle()))
                .collect(Collectors.toList());

        List<UserDTO> userDTOs = sharedCollection.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());

        return new SharedCollectionDTO(
                sharedCollection.getId(),
                sharedCollection.getTitle(),
                mediaDTOs,
                userDTOs
        );
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
    public void addUserToSharedList(Long sharedListId, User user) {
        SharedCollection sharedCollection = sharedCollectionRepository.findById(sharedListId)
                .orElseThrow(() -> new RuntimeException("SharedCollection not found"));
        sharedCollection.getUsers().add(user);
        sharedCollectionRepository.save(sharedCollection);
        reviewService.createReviewsForUserInSharedList(user, sharedCollection);
    }
}
