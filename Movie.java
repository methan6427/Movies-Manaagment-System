package com.example.ds4;


public class Movie implements Comparable<Movie>{
    private String title;
    private String description;
    private int releaseYear;
    private double rating;
    private String posterUrl;

    // Constructor
    public Movie(String title, String description, int releaseYear, double rating, String posterUrl) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.posterUrl=posterUrl;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 10.0) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 0.0 and 10.0");
        }
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    // toString method for displaying movie details
    @Override
    public String toString() {
        return String.format("Title: %s\nDescription: %s\nRelease Year: %d\nRating: %.1f",
                title, description, releaseYear, rating);
    }

    // Equals and HashCode for comparisons (optional but recommended)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return false;

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public int compareTo(Movie o) {
        return this.title.compareTo(o.getTitle());
    }

}
