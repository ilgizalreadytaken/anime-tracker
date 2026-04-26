package ru.anime.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.anime.tracker.model.Anime;
import ru.anime.tracker.service.AnimeService;
import ru.anime.tracker.dto.AnimeTopDto;

import java.util.List;

@RestController // говорит Spring: это REST API контроллер (возвращает JSON)
@RequestMapping("/api/anime")
public class AnimeController {
    private final AnimeService service;

    public AnimeController(AnimeService service) {
        this.service = service;
    }

    // ПОЛУЧИТЬ ВСЕ АНИМЕ (С ПАГИНАЦИЕЙ)
    @GetMapping
    public Page<Anime> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public Anime getById(@PathVariable Long id) {
        // берём id из URL и ищем аниме
        return service.getById(id);
    }

    @PostMapping
    public Anime create(@Valid @RequestBody Anime anime) {
        // @RequestBody = JSON → Java объект
        // @Valid = проверка полей (валидация)
        return service.save(anime);
    }

    @PutMapping("/{id}")
    public Anime update(@PathVariable Long id,
                        @Valid @RequestBody Anime anime) {
        // берём id из URL + новые данные из JSON
        return service.update(id, anime);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/top")
    public List<AnimeTopDto> top() {
        return service.getTopAnimeWithRating();
    }

    @GetMapping("/search")
    public List<Anime> searchByGenre(@RequestParam String genre) {
        return service.searchByGenre(genre);
    }
}