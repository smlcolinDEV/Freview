package com.example.freview.repositories;

import com.example.freview.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepostiory extends JpaRepository<Media,Long> {
}
