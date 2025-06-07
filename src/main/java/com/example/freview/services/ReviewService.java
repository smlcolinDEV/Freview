package com.example.freview.services;

import com.example.freview.dto.ReviewDTO;
import com.example.freview.enums.WatchedStatus;
import com.example.freview.models.Media;
import com.example.freview.models.Review;
import com.example.freview.models.SharedCollection;
import com.example.freview.models.User;
import com.example.freview.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
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
            review.setRating(0.0);
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
            review.setRating(0.0);
            review.setCreatedDate(LocalDate.now());
            review.setWatchedStatus(WatchedStatus.NOT_WATCHED);
            review.setUser(user);
            review.setMedia(media);
            review.setSharedCollection(sharedCollection);
            reviewRepository.save(review);
        }
    }
}
