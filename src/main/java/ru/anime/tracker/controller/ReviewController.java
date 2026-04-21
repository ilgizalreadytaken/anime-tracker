package ru.anime.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.anime.tracker.model.Review;
import ru.anime.tracker.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    // ➜ Добавить отзыв к аниме
    @PostMapping("/{animeId}")
    public Review addReview(
            @PathVariable Long animeId,
            @Valid @RequestBody Review review
    ) {
        return service.addReview(animeId, review);
    }

    // ➜ Получить отзывы конкретного аниме
    @GetMapping("/{animeId}")
    public List<Review> getReviewsByAnime(@PathVariable Long animeId) {
        return service.getReviewsByAnimeId(animeId);
    }
}