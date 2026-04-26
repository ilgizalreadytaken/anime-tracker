package ru.anime.tracker.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.anime.tracker.model.Anime;
import ru.anime.tracker.model.Review;
import ru.anime.tracker.repository.AnimeRepository;
import ru.anime.tracker.repository.ReviewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AnimeRepository animeRepository;

    public ReviewService(ReviewRepository reviewRepository, AnimeRepository animeRepository) {
        this.reviewRepository = reviewRepository;
        this.animeRepository = animeRepository;
    }

    // добавить отзыв
    public Review addReview(Long animeId, Review review) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        review.setAnime(anime);
        review.setDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    // список отзывов
    public List<Review> getReviewsByAnimeId(Long animeId) {
        if (!animeRepository.existsById(animeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        return reviewRepository.findByAnime_Id(animeId);
    }

    // средний рейтинг
    public Double getAverageRating(Long animeId) {
        if (!animeRepository.existsById(animeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        return reviewRepository.getAverageRating(animeId);
    }
}