package ru.anime.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.anime.tracker.model.Anime;
import ru.anime.tracker.repository.AnimeRepository;
import ru.anime.tracker.repository.ReviewRepository;

import java.util.List;

@Service
public class AnimeService {

    private final AnimeRepository repo;
    private final ReviewRepository reviewRepository; // 👈 ДОБАВИЛИ

    public AnimeService(AnimeRepository repo, ReviewRepository reviewRepository) {
        this.repo = repo;
        this.reviewRepository = reviewRepository;
    }

    // ✅ ПАГИНАЦИЯ
    public Page<Anime> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    public Anime getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Anime save(Anime anime) {
        return repo.save(anime);
    }

    public Anime update(Long id, Anime newAnime) {
        Anime anime = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        anime.setTitle(newAnime.getTitle());
        anime.setGenre(newAnime.getGenre());
        anime.setYear(newAnime.getYear());
        anime.setEpisodes(newAnime.getEpisodes());
        anime.setStatus(newAnime.getStatus());

        return repo.save(anime);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        repo.deleteById(id);
    }

    public List<Anime> getTopAnime() {
        return repo.findTopAnime();
    }

    public List<Anime> searchByGenre(String genre) {
        return repo.findByGenreContainingIgnoreCase(genre);
    }

    // ⭐ ВОТ ЭТО ТЫ ХОТЕЛ
    public Double getAnimeRating(Long animeId) {
        Double avg = reviewRepository.getAverageRating(animeId);
        return avg != null ? avg : 0.0;
    }
}