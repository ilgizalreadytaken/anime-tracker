package ru.anime.tracker.dto;

public class AnimeTopDto {
    private Long id;
    private String title;
    private String genre;
    private Double averageRating;

    public AnimeTopDto(Long id, String title, String genre, Double averageRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Double getAverageRating() { return averageRating; }
}