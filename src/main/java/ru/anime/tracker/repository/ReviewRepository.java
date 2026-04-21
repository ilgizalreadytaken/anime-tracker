package ru.anime.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anime.tracker.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAnimeId(Long animeId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.anime.id = :animeId")
    Double getAverageRating(@Param("animeId") Long animeId);
}