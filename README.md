# Anime Tracker API

REST API для трекинга аниме с отзывами и рейтингом.

## Технологии

Java 17, Spring Boot, Spring Data JPA, PostgreSQL, Docker, Docker Compose

Как запустить проект

```bash
docker compose up --build

```

Остановить проект
```
docker compose down
```

Доступ к API
```http://localhost:8099/api/anime```

Swagger UI
```http://localhost:8099/swagger-ui/index.html```

## API Endpoints

### Аниме

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/anime` | Получить список всех аниме (с пагинацией) |
| GET | `/api/anime?page=0&size=10` | Пагинация: указать страницу и размер |
| GET | `/api/anime/{id}` | Получить аниме по ID |
| POST | `/api/anime` | Создать новое аниме |
| PUT | `/api/anime/{id}` | Обновить аниме по ID |
| DELETE | `/api/anime/{id}` | Удалить аниме по ID |
| GET | `/api/anime/top` | Топ аниме по средней оценке |
| GET | `/api/anime/search?genre=Sci-Fi` | Поиск аниме по жанру |


### Отзывы

| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/reviews/{animeId}` | Добавить отзыв к аниме с указанным ID |
| GET | `/api/reviews/{animeId}` | Получить все отзывы для конкретного аниме |
| GET | `/api/reviews/average/{animeId}` | Получить среднюю оценку аниме |

## JSON примеры
### Создание / обновление аниме

``` {
"title": "Cowboy Bebop",
"genre": "Sci-Fi",
"year": 1998,
"episodes": 26,
"status": "COMPLETED"
} 
```

### Добавление отзыва
```{
  "rating": 10,
  "comment": "Отличное аниме!"
}
```

## Curl примеры запросов
### Получить все аниме
```curl http://localhost:8099/api/anime```

### Создать аниме
```curl -X POST http://localhost:8099/api/anime ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Naruto\",\"genre\":\"Shounen\",\"year\":2002,\"episodes\":220,\"status\":\"WATCHING\"}"
  ```

### Получить аниме по ID
```curl http://localhost:8099/api/anime/1 ```

### Обновить аниме
```curl -X PUT http://localhost:8099/api/anime/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Naruto Shippuden\",\"genre\":\"Shounen\",\"year\":2007,\"episodes\":500,\"status\":\"COMPLETED\"}"
  ```

### Удалить аниме
```curl -X DELETE http://localhost:8099/api/anime/1```

### Получить топ аниме по рейтингу
```curl http://localhost:8099/api/anime/top```

### Поиск аниме по жанру
```curl "http://localhost:8099/api/anime/search?genre=Sci-Fi"```

### Добавить отзыв к аниме с id = 1
```curl -X POST http://localhost:8099/api/reviews/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"rating\":10,\"comment\":\"Отличное аниме\"}"
  ```
### Получить все отзывы для аниме с id = 1
```curl http://localhost:8099/api/reviews/1```

### Получить средний рейтинг аниме с id = 1
```curl http://localhost:8099/api/reviews/average/1```

## Структура данных
### Anime
```
id — уникальный идентификатор (генерируется автоматически)

title — название (уникальное, обязательное)

genre — жанр (обязательный)

year — год выпуска (не меньше 1900)

episodes — количество эпизодов (не меньше 1)

status — статус просмотра (например, COMPLETED, WATCHING)
```

### Review
```
id — уникальный идентификатор

rating — оценка от 1 до 10 (обязательно)

comment — комментарий (обязательный)

date — дата добавления (проставляется автоматически)

anime — связь с Anime (в JSON не отображается)
```

### Связи между сущностями
```
Один Anime может иметь много Review

Один Review относится к одному Anime

При удалении Anime удаляются все его отзывы (каскад)
```

## Docker
```    
anime-app — Spring Boot приложение (запускается на порту 8099)

anime-db — PostgreSQL 16 (порт 5433 проброшен на 5432 внутри контейнера)

Данные сохраняются через том pgdata и не удаляются при остановке контейнеров.
```