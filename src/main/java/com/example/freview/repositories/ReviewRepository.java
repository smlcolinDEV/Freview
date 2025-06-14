package com.example.freview.repositories;

import com.example.freview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(Long userId);

    @Query("SELECT r FROM Review r " +
            "JOIN r.media m " +
            "WHERE EXISTS (SELECT 1 FROM SharedCollection sc JOIN sc.mediaList ml WHERE sc.id = :sharedListId AND ml = m) " +
            "AND m.id = :mediaId")
    List<Review> findReviewsByMediaIdAndSharedListId(@Param("mediaId") Long mediaId, @Param("sharedListId") Long sharedListId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.media.id = :mediaId AND r.sharedCollection.id = :sharedCollectionId")
    Optional<Review> findByUserAndMediaAndSharedCollection(
            @Param("userId") Long userId,
            @Param("mediaId") Long mediaId,
            @Param("sharedCollectionId") Long sharedCollectionId
    );

}
