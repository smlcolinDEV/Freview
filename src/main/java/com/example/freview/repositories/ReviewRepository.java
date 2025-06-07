package com.example.freview.repositories;

import com.example.freview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    public List<Review> findByUserId(Long userId);
    @Query("SELECT r FROM Review r " +
            "JOIN r.media m " +
            "WHERE EXISTS (SELECT 1 FROM SharedCollection sc JOIN sc.mediaList ml WHERE sc.id = :sharedListId AND ml = m) " +
            "AND m.id = :mediaId")
    List<Review> findReviewsByMediaIdAndSharedListId(@Param("mediaId") Long mediaId, @Param("sharedListId") Long sharedListId);
}
