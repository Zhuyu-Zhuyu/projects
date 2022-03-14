package com.zhuyu.ssm.dao;

import com.zhuyu.ssm.domain.Movie;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovieDao {
    //    查询所有电影
    @Select("select * from movie")
    public List<Movie> findAll();

    //    根据id查电影
    @Select("select * from movie where id = #{id}")
    public Movie FindMovieByID(Integer id);

    //    修改电影信息
    @Update("update movie set moviename=#{moviename}, director=#{director}, actor=#{actor} where id=#{id}")
    public void updateMovie(Movie movie);

//    增加电影
    @Insert("insert into movie values(null,#{moviename},#{director},#{actor}")
    public void insertMovie(Movie movie);

//    删除电影
    @Delete("delete from movie where id=#{id}")
    public void deleteMovie(Integer id);
}
