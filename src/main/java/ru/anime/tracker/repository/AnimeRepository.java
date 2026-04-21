package ru.anime.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.anime.tracker.model.Anime;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    @Query("""
       SELECT a
       FROM Anime a
       LEFT JOIN a.reviews r
       GROUP BY a
       ORDER BY COALESCE(AVG(r.rating), 0) DESC
       """)
    List<Anime> findTopAnime();

    List<Anime> findByGenreContainingIgnoreCase(String genre);
}