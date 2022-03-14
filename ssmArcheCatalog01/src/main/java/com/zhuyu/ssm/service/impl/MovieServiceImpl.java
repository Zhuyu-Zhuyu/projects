package com.zhuyu.ssm.service.impl;

import com.zhuyu.ssm.dao.ImovieDao;
import com.zhuyu.ssm.domain.Movie;
import com.zhuyu.ssm.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("moviesService")
public class MovieServiceImpl implements IMovieService {
    @Autowired
    private ImovieDao dao;

    @Override
    public List<Movie> findAll() {
        System.out.println("MovieServiceImpl   findAll方法");
        return dao.findAll();
    }

    @Override
    public Movie FindMovieByID(Integer id) {
        return dao.FindMovieByID(id);
    }

    @Override
    public void updateMovie(Movie movie) {
        dao.updateMovie(movie);
    }

    @Override
    public void insertMovie(Movie movie) {
        dao.insertMovie(movie);
    }

    @Override
    public void deleteMovie(Integer id) {
        dao.deleteMovie(id);
    }
}
