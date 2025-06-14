package com.example.freview.controllers;

import com.example.freview.dto.ReviewDTO;
import com.example.freview.dto.UpdatedReviewRequestDTO;
import com.example.freview.models.Review;
import com.example.freview.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Review createReview(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @PutMapping("/id")
    public Review modifyReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.update(id, review);
    }

    @PatchMapping("/id")
    public Review patchUser(@RequestBody UpdatedReviewRequestDTO request) {
        return reviewService.updateReview(request);
    }

    @DeleteMapping("/id")
    public void deleteUser(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }


}