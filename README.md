# Anime Tracker API

REST API для трекинга аниме с отзывами и рейтингом.

## Технологии

Java 17, Spring Boot, Spring Data JPA, PostgreSQL, Docker, Docker Compose

## Как запустить проект

```bash
docker compose up --build
```

## Остановить проект

```bash
docker compose down
```

## Доступ к API

```text
http://localhost:8099/api/anime
```

## API Endpoints

### Anime

- GET `/api/anime` — получить все аниме
- GET `/api/anime/{id}` — получить аниме по ID
- POST `/api/anime` — создать аниме
- PUT `/api/anime/{id}` — обновить аниме
- DELETE `/api/anime/{id}` — удалить аниме
- GET `/api/anime/top` — топ аниме по рейтингу
- GET `/api/anime/search?genre=Sci-Fi` — поиск по жанру

### Reviews

- POST `/api/anime/{animeId}/reviews` — добавить отзыв
- GET `/api/anime/{animeId}/reviews` — получить отзывы

## JSON примеры

### Anime

```json
{
  "title": "Naruto",
  "genre": "Shounen",
  "year": 2002,
  "episodes": 220,
  "status": "WATCHING"
}
```

### Review

```json
{
  "rating": 10,
  "comment": "Отличное аниме"
}
```

## Curl примеры запросов

### Получить все аниме

```bash
curl http://localhost:8099/api/anime
```

### Создать аниме

```bash
curl -X POST http://localhost:8099/api/anime ^
-H "Content-Type: application/json" ^
-d "{\"title\":\"Naruto\",\"genre\":\"Shounen\",\"year\":2002,\"episodes\":220,\"status\":\"WATCHING\"}"
```

### Добавить отзыв

```bash
curl -X POST http://localhost:8099/api/anime/1/reviews ^
-H "Content-Type: application/json" ^
-d "{\"rating\":10,\"comment\":\"Отличное аниме\"}"
```

### Получить топ аниме

```bash
curl http://localhost:8099/api/anime/top
```

## Структура данных

### Anime

- id — уникальный идентификатор
- title — название (unique)
- genre — жанр
- year — год выпуска
- episodes — количество эпизодов
- status — статус просмотра
- reviews — список отзывов

### Review

- id — уникальный идентификатор
- rating — оценка (1–10)
- comment — комментарий
- date — дата добавления
- anime — связь с Anime

## Связи между сущностями

- Один Anime может иметь много Review
- Один Review относится к одному Anime

## Docker

- anime-app — Spring Boot приложение
- anime-db — PostgreSQL база данных

Данные сохраняются через volume:

```text
pgdata
```

## Примечания

- rating строго от 1 до 10
- title должен быть уникальным
- данные хранятся в PostgreSQL
- API работает через JSON