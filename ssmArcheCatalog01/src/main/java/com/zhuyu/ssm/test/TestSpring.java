package com.zhuyu.ssm.test;

import com.zhuyu.ssm.domain.Movie;
import com.zhuyu.ssm.service.IMovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestSpring {

    @Autowired
    private IMovieService service;

    @Test
    public void testFindAll(){
        List<Movie> movies = service.findAll();
        for (Movie movie :movies){
            System.out.println(movie);
        }
    }
    //根据ID查电影
    @Test
    public void testFindMovieById(){
        Integer id = 5;
        System.out.println(service.FindMovieByID(5));
    }
    //    修改电影信息
    @Test
    public void testUpdateMovie(){
        Movie movie = new Movie();
        movie.setMoviename("艾米丽");
        movie.setDirector("we");
        movie.setActor("奥黛丽");
        service.updateMovie(movie);
    }
    //    增加电影
    @Test
    public void testInsertMovie(){
        Movie movie = new Movie();
        movie.setMoviename("爱有来生");
        movie.setDirector("俞飞鸿");
        movie.setActor("俞飞鸿");
        service.insertMovie(movie);
    }
    //    删除电影
    @Test
    public void testDeleteMovie(){
        service.deleteMovie(1);
    }
}
