package com.zhuyu.ssm.controller;

import com.zhuyu.ssm.domain.Movie;
import com.zhuyu.ssm.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movieController")
public class MovieController {
    @Autowired
    private IMovieService service;
//查询所有
    @RequestMapping("findAll")
    public String findAll(Model model){
        System.out.println("Controller findAll");
        List<Movie> movies = service.findAll();
        model.addAttribute("movies",movies);
        return "success";
    }
//    根据id查询
    @RequestMapping("findOne")
    public String findOne(Integer id, Model model){
        Movie movie = service.FindMovieByID(id);
        model.addAttribute(movie);
        return "success";
    }
//    修改
    @RequestMapping("updataMovie")
    public String updateMovie(Model model,Movie movie){
        service.updateMovie(movie);
        model.addAttribute(movie);
        return "success";
    }
//    加入
    @RequestMapping("addMovie")
    public String addMovie(Model model, Movie movie){
        service.insertMovie(movie);
       return "success";
    }

//    删除
    @RequestMapping("deleteMovie")
    public String deleteMovie(Integer id, Model model){
        service.deleteMovie(id);
        return "success";
    }
}
