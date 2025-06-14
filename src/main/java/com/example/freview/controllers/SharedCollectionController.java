package com.example.freview.controllers;

import com.example.freview.dto.CreateSharedCollectionRequest;
import com.example.freview.dto.ReviewDTO;
import com.example.freview.dto.SharedCollectionDTO;
import com.example.freview.dto.UserDTO;
import com.example.freview.models.Media;
import com.example.freview.models.SharedCollection;
import com.example.freview.services.EmailService;
import com.example.freview.services.SharedCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shared")
public class SharedCollectionController {
    private final SharedCollectionService sharedCollectionService;
    private final EmailService emailService;

    public SharedCollectionController(SharedCollectionService sharedCollectionService, EmailService emailService) {
        this.sharedCollectionService = sharedCollectionService;
        this.emailService = emailService;
    }

    @GetMapping
    public List<SharedCollectionDTO> getAllSharedCollections() {
        return sharedCollectionService.getSharedCollections();
    }

    @GetMapping("/{userId}/shared-collections")
    public List<SharedCollectionDTO> getSharedCollectionsByUserId(@PathVariable Long userId) {
        return sharedCollectionService.getSharedCollectionsByUserId(userId);
    }

    @GetMapping("/{sharedCollectionId}")
    public SharedCollectionDTO getSharedCollectionById(@PathVariable Long sharedCollectionId) {
        return sharedCollectionService.findSharedCollectionById(sharedCollectionId);
    }

    @PostMapping("/newSharedList")
    public ResponseEntity<SharedCollectionDTO> createSharedCollection(
            @Valid @RequestBody CreateSharedCollectionRequest request
    ) {
        SharedCollection collection = sharedCollectionService.createSharedCollection(request.getTitle());

        SharedCollectionDTO dto = new SharedCollectionDTO();
        dto.setId(collection.getId());
        dto.setName(collection.getName());
        dto.setMediaList(new ArrayList<>()); // vide

        dto.setUsers(
                collection.getUsers().stream()
                        .map(user -> new UserDTO(user.getId(), user.getUsername()))
                        .collect(Collectors.toList())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{sharedListId}/media")
    public ResponseEntity<Void> addMediaToSharedList(
            @PathVariable Long sharedListId,
            @Valid @RequestBody Media media
    ) {
        sharedCollectionService.addMediaToSharedList(sharedListId, media);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sharedListId}/users")
    public ResponseEntity<Void> addUserToSharedList(
            @PathVariable Long sharedListId,
            @Valid @RequestBody Long userId
    ) {
        sharedCollectionService.addUserToSharedList(sharedListId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sharedListId}/add-movie")
    public ResponseEntity<String> addMovieFromTmdbToSharedList(
            @PathVariable Long sharedListId,
            @RequestParam Long movieId
    ) {
        sharedCollectionService.addMovieFromTmdbToSharedList(sharedListId, movieId);
        return ResponseEntity.ok("Movie added to shared collection.");
    }

    @PostMapping("/{sharedListId}/add-serie")
    public ResponseEntity<String> addSerieFromTmdbToSharedList(
            @PathVariable Long sharedListId,
            @RequestParam Long serieId
    ) {
        sharedCollectionService.addSerieFromTmdbToSharedList(sharedListId, serieId);
        return ResponseEntity.ok("Serie added to shared collection.");
    }

    @PostMapping("/{sharedListId}/join")
    public ResponseEntity<String> joinSharedCollection(@PathVariable Long sharedListId) {
        sharedCollectionService.joinSharedCollectionById(sharedListId);
        return ResponseEntity.ok("L'utilisateur a bien rejoint la collection partagée.");
    }

    @PostMapping("/{sharedListId}/invite")
    public ResponseEntity<String> inviteUserToSharedList(
            @PathVariable Long sharedListId,
            @RequestParam String email
    ) {
        String inviteUrl = "http://localhost:8080/join?sharedListId=" + sharedListId;
        emailService.sendInvitationEmail(email, inviteUrl);
        return ResponseEntity.ok("Invitation envoyée à " + email);
    }

    @PostMapping("/{sharedCollectionId}/media/{mediaId}/review")
    public ResponseEntity<String> updateReviewToMediaInSharedCollection(
            @PathVariable Long sharedCollectionId,
            @PathVariable Long mediaId,
            @RequestBody ReviewDTO reviewDTO) {

        sharedCollectionService.updateReviewForMediaInSharedCollection(
                sharedCollectionId,
                mediaId,
                reviewDTO.getComment(),
                reviewDTO.getRating()
        );

        return ResponseEntity.ok("Review ajoutée avec succès !");
    }

    @PutMapping("/{sharedListId}")
    public ResponseEntity<SharedCollectionDTO> updateSharedCollection(
            @PathVariable Long sharedListId,
            @RequestBody SharedCollectionDTO dto
    ) {
        SharedCollectionDTO updated = sharedCollectionService.updateSharedCollection(sharedListId, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/id")
    public void deleteSharedList(@PathVariable Long sharedListId) {
        sharedCollectionService.delete(sharedListId);
    }
}
