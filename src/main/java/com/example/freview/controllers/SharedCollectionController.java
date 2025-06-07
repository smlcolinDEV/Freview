package com.example.freview.controllers;

import com.example.freview.dto.SharedCollectionDTO;
import com.example.freview.models.Media;
import com.example.freview.models.SharedCollection;
import com.example.freview.models.User;
import com.example.freview.services.MovieService;
import com.example.freview.services.SharedCollectionService;
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
}
