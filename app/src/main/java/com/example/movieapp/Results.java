package com.example.movieapp;

public class Results {

    private String poster_path;
    private String id;
    private String title;
    private String original_title;
    private String vote_average;
    private String overview;
    private String release_date;
    private String backdrop_path;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
