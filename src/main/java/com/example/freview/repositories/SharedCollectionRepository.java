package com.example.freview.repositories;

import com.example.freview.models.SharedCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedCollectionRepository extends JpaRepository<SharedCollection, Long> {
    @Query("SELECT sc FROM SharedCollection sc JOIN sc.users u WHERE u.id = :userId")
    List<SharedCollection> findSharedCollectionsByUserId(@Param("userId") Long userId);
}
