package com.example.movieapp;

import androidx.annotation.NonNull;

import java.util.List;

public class Movies {
    private int page;
    private int total_results;
    private int total_pages;
    private List<Results> results;
    private List<Trailer> trailerList;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Results> getResults() {
        return results;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }
}
