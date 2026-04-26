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

    @PostMapping("/{animeId}")
    public Review addReview(
            @PathVariable Long animeId,
            // берёт animeId из URL
            @Valid @RequestBody Review review
            // @RequestBody — JSON из запроса превращается в Java объект Review
    ) {
        return service.addReview(animeId, review);
    }

    @GetMapping("/{animeId}")
    public List<Review> getReviewsByAnime(@PathVariable Long animeId) {
        return service.getReviewsByAnimeId(animeId);
    }

    @GetMapping("/test")
    public String test() {
        return "REVIEWS OK";
    }

    @GetMapping("/average/{animeId}")
    public Double getAverage(@PathVariable Long animeId) {
        return service.getAverageRating(animeId);
    }
}