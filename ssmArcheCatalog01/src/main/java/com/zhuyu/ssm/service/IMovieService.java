package com.zhuyu.ssm.service;

import com.zhuyu.ssm.domain.Movie;

import java.util.List;

public interface IMovieService {

    public List<Movie> findAll();
    public Movie FindMovieByID(Integer id);
    public void updateMovie(Movie movie);
    public void insertMovie(Movie movie);
    public void deleteMovie(Integer id);
}
