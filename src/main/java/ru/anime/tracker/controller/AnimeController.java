package ru.anime.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.anime.tracker.model.Anime;
import ru.anime.tracker.service.AnimeService;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    private final AnimeService service;

    public AnimeController(AnimeService service) {
        this.service = service;
    }

    // ✅ ПАГИНАЦИЯ
    @GetMapping
    public Page<Anime> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public Anime getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Anime create(@Valid @RequestBody Anime anime) {
        return service.save(anime);
    }

    @PutMapping("/{id}")
    public Anime update(@PathVariable Long id, @Valid @RequestBody Anime anime) {
        return service.update(id, anime);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/top")
    public List<Anime> top() {
        return service.getTopAnime();
    }

    @GetMapping("/search")
    public List<Anime> searchByGenre(@RequestParam String genre) {
        return service.searchByGenre(genre);
    }
}