package com.example.freview.controllers;

import com.example.freview.dto.ReviewDTO;
import com.example.freview.models.Review;
import com.example.freview.services.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{userId}/reviews")
    public List<Review> getReviewsByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }
    @GetMapping("/{sharedListId}/media/{mediaId}/reviews")
    public List<ReviewDTO> getReviewsByMediaIdAndSharedListId(
            @PathVariable Long sharedListId,
            @PathVariable Long mediaId) {
        return reviewService.getReviewsByMediaIdAndSharedListId(mediaId, sharedListId);
    }
}