package com.zhuyu.ssm.test;

import com.zhuyu.ssm.dao.ImovieDao;
import com.zhuyu.ssm.domain.Movie;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMybatis {
    @Autowired
    ImovieDao dao;
    //        执行findAll方法，获取movie List
    @Test
    public void testFindAll() {
        List<Movie> movies = dao.findAll();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
//根据ID查电影
    @Test
    public void testFindMovieById(){
        Integer id = 2;
        System.out.println(dao.FindMovieByID(id));
    }
//    修改电影信息
    @Test
    public void testUpdateMovie(){
        Movie movie = dao.FindMovieByID(3);
        movie.setMoviename("艾米丽");
        movie.setDirector("we");
        movie.setActor("奥黛丽");
        dao.updateMovie(movie);
    }
//    增加电影
    @Test
    public void testInsertMovie(){
        Movie movie = new Movie();
        movie.setMoviename("爱有来生");
        movie.setDirector("俞飞鸿");
        movie.setActor("俞飞鸿");
        dao.insertMovie(movie);
    }
//    删除电影
    @Test
    public void testDeleteMovie(){
        dao.deleteMovie(1);
    }

}
