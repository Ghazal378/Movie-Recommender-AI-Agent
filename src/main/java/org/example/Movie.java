package org.example;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private float score;
    private String releaseDate;
    private int runTime;
    private boolean isAdult;
    private String imdbId;
    private String language;
    private String summary;
    private String posterPath;
    private String tagLine;
    private List<String> genres;


    public Movie(String id, String title, float score, String releaseDate, int runTime,
                 boolean isAdult, String imdbId, String language, String summary,
                 String posterPath, String tagLine, List<String> genres) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
        this.isAdult = isAdult;
        this.imdbId = imdbId;
        this.language = language;
        this.summary = summary;
        this.posterPath = posterPath;
        this.tagLine = tagLine;
        this.genres = genres;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTagline() {
        return tagLine;
    }


}
