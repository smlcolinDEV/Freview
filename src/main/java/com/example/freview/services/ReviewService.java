package com.example.freview.services;

import com.example.freview.dto.ReviewDTO;
import com.example.freview.dto.UpdatedReviewRequestDTO;
import com.example.freview.enums.WatchedStatus;
import com.example.freview.exceptions.ResourceNotFoundException;
import com.example.freview.models.Media;
import com.example.freview.models.Review;
import com.example.freview.models.SharedCollection;
import com.example.freview.models.User;
import com.example.freview.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    public Review getReviewById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }
    public List<ReviewDTO> getReviewsByMediaIdAndSharedListId(Long mediaId, Long sharedListId) {
        return reviewRepository.findReviewsByMediaIdAndSharedListId(mediaId,sharedListId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getWatchedStatus()

        );
    }
    @Transactional
    public void createReviewsForMediaInSharedList(Media media, SharedCollection sharedCollection) {
        for (User user : sharedCollection.getUsers()) {
            Review review = new Review();
            review.setComment("Default comment");
            review.setRating(null);
            review.setCreatedDate(LocalDate.now());
            review.setWatchedStatus(WatchedStatus.NOT_WATCHED);
            review.setUser(user);
            review.setMedia(media);
            review.setSharedCollection(sharedCollection);
            reviewRepository.save(review);
        }
    }

    @Transactional
    public void createReviewsForUserInSharedList(User user, SharedCollection sharedCollection) {
        for (Media media : sharedCollection.getMediaList()) {
            Review review = new Review();
            review.setComment("Default comment");
            review.setRating(null);
            review.setCreatedDate(LocalDate.now());
            review.setWatchedStatus(WatchedStatus.NOT_WATCHED);
            review.setUser(user);
            review.setMedia(media);
            review.setSharedCollection(sharedCollection);
            reviewRepository.save(review);
        }
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }
    public Review update(Long id,Review review){
        var existingReview = reviewRepository.findById(review.getId());
        if(existingReview.isPresent()){
            Review updatedReview = existingReview.get();
            updatedReview.setComment(review.getComment());
            updatedReview.setRating(review.getRating());
            updatedReview.setWatchedStatus(review.getWatchedStatus());
            return reviewRepository.save(updatedReview);
        }
        else{
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
    }

    public Review updateReview(UpdatedReviewRequestDTO request) {
        var reviewToUpdate =  getReviewById(request.getId());
        reviewToUpdate.setComment(request.getComment());
        return reviewRepository.save(reviewToUpdate);
    }


    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
