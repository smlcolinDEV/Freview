package com.example.freview.controllers;

import com.example.freview.dto.SharedCollectionDTO;
import com.example.freview.models.Media;
import com.example.freview.models.SharedCollection;
import com.example.freview.models.User;
import com.example.freview.services.MovieService;
import com.example.freview.services.SharedCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shared")
public class SharedCollectionController {
    private final SharedCollectionService sharedCollectionService;

    public SharedCollectionController(SharedCollectionService sharedCollectionService) {
        this.sharedCollectionService = sharedCollectionService;
    }

    @GetMapping
    public List<SharedCollectionDTO> getAllSharedCollections() {
        return sharedCollectionService.getSharedCollections();
    }

    @GetMapping("/{userId}/shared-collections")
    public List<SharedCollectionDTO> getSharedCollectionsByUserId(@PathVariable Long userId) {
        return sharedCollectionService.getSharedCollectionsByUserId(userId);
    }

    @PostMapping("/{sharedListId}/media")
    public void addMediaToSharedList(@PathVariable Long sharedListId, @RequestBody Media media) {
        sharedCollectionService.addMediaToSharedList(sharedListId, media);
    }

    @PostMapping("/{sharedListId}/users")
    public void addUserToSharedList(@PathVariable Long sharedListId, @RequestBody User user) {
        sharedCollectionService.addUserToSharedList(sharedListId, user);
    }
    @PostMapping("/{sharedListId}/add-media")
    public ResponseEntity<String> addMediaToSharedList(
            @PathVariable Long sharedListId,
            @RequestParam int tmdbMediaId) {

        try {
            sharedCollectionService.addMediaFromTmdbToSharedList(sharedListId, tmdbMediaId);
            return ResponseEntity.ok("Media added to the shared list successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add media to the shared list: " + e.getMessage());
        }
    }
    @DeleteMapping("/id")
    public void deleteSharedList(@PathVariable Long sharedListId) {
        sharedCollectionService.delete(sharedListId);
    }
}
