package ru.anime.tracker.service;
// Spring Data (работа с пагинацией)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// HTTP ошибки
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
// Модель
import ru.anime.tracker.model.Anime;
// Репозитории (работа с БД через Hibernate / JPA)
import ru.anime.tracker.repository.AnimeRepository;
import ru.anime.tracker.repository.ReviewRepository;
import java.util.List;

import ru.anime.tracker.dto.AnimeTopDto;
import java.util.stream.Collectors;

// Помечаем класс как Service слой. Spring автоматически создаёт этот бин и управляет им
@Service
public class AnimeService {

    // Репозиторий для работы с таблицей anime, через него идёт вся работа с базой (CRUD)
    private final AnimeRepository repo;

    // Репозиторий для работы с отзывами (reviews), нужен, чтобы считать рейтинг аниме
    private final ReviewRepository reviewRepository;

    // Dependency Injection через конструктор, Spring сам передаёт сюда нужные зависимости
    public AnimeService(AnimeRepository repo, ReviewRepository reviewRepository) {
        this.repo = repo;
        this.reviewRepository = reviewRepository;
    }

    // ПАГИНАЦИЯ
    public Page<Anime> getAll(int page, int size) {

        // Pageable — объект, который описывает: какую страницу и сколько элементов нужно взять
        Pageable pageable = PageRequest.of(page, size);

        return repo.findAll(pageable);
    }

    public Anime getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Anime not found"
                ));
    }

    public Anime save(Anime anime) {
        // save работает и как INSERT, и как UPDATE:
        // - если id = null → INSERT
        // - если id существует → UPDATE

        return repo.save(anime);
    }

    public Anime update(Long id, Anime newAnime) {

        // Сначала ищем старую запись в БД
        Anime anime = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Anime not found"
                ));

        // Обновляем поля существующего объекта
        // мы не создаём новый объект, а меняем старый
        // Hibernate отслеживает изменения и сделает UPDATE

        anime.setTitle(newAnime.getTitle());
        anime.setGenre(newAnime.getGenre());
        anime.setYear(newAnime.getYear());
        anime.setEpisodes(newAnime.getEpisodes());
        anime.setStatus(newAnime.getStatus());

        return repo.save(anime);
    }

    public void delete(Long id) {

        // 📌 Проверяем существует ли запись
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Anime not found"
            );
        }

        repo.deleteById(id);

        // SQL внутри Hibernate:
        // DELETE FROM anime WHERE id = ?
    }

    public List<Anime> searchByGenre(String genre) {

        // Spring Data сам генерирует SQL:
        // - Containing → LIKE %genre%
        // - IgnoreCase → без учёта регистра

        return repo.findByGenreContainingIgnoreCase(genre);
    }

    public Double getAnimeRating(Long animeId) {

        //  Получаем среднее значение рейтинга из таблицы review
        Double avg = reviewRepository.getAverageRating(animeId);

        // Если отзывов нет → AVG вернёт null
        // поэтому возвращаем 0.0 чтобы не сломать фронт
        return avg != null ? avg : 0.0;
    }

    public List<AnimeTopDto> getTopAnimeWithRating() {
        // получаем топ-аниме (уже отсортированы по среднему рейтингу)
        List<Anime> topAnime = repo.findTopAnime();

        // преобразуем в DTO с рейтингом
        return topAnime.stream()
                .map(anime -> {
                    Double avg = getAnimeRating(anime.getId());
                    return new AnimeTopDto(
                            anime.getId(),
                            anime.getTitle(),
                            anime.getGenre(),
                            avg
                    );
                })
                .collect(Collectors.toList());
    }
}