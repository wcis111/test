package cn.edu.scnu.service;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.mapper.MovieMapper;
import cn.edu.scnu.repository.MovieRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService extends ServiceImpl<MovieMapper, Movie> {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getTopRatedMovies() {
        return movieRepository.findAllByOrderByGradeDesc();
    }


    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public Page<Movie> findAllMovies(int pageNo, int pageSize, String movieName) {
        Page<Movie> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        if (movieName != null && !movieName.isEmpty()) {
            queryWrapper.like("movieName", movieName);
        }
        return page(page, queryWrapper);
    }
    public Movie findMoiveBYID(String movieid){
        Object object=redisTemplate.opsForValue().get("flower_"+movieid);
        if(object!=null){
            return (Movie) object;
        }
        else{
            Movie flower=movieMapper.selectById(movieid);
            redisTemplate.opsForValue().set("flower_"+movieid,flower);
            return flower;
        }
    }
    public Map<String,Object> queryPage(String movieName,Integer pageNo, Integer pageSize){
        QueryWrapper<Movie> queryWrapper=new QueryWrapper<>();
        if(!"".equals(movieName)&&movieName!="")queryWrapper.like("moviename",movieName);
        QueryWrapper<Movie> movieId = queryWrapper.orderByDesc("movieid");
        int count=movieMapper.selectCount(queryWrapper).intValue();
        Page<Movie> page=new Page<Movie>(pageNo,pageSize);
        Page<Movie> moviePage=movieMapper.selectPage(page,queryWrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("count",count);
        map.put("recourds",page.getRecords());
        return map;
    }

    public Movie findMovieByID(String movieid) {
        Object object = redisTemplate.opsForValue().get("flower_" + movieid);
        if (object != null) {
            return (Movie) object;
        } else {
            Movie flower = movieMapper.selectById(movieid);
            redisTemplate.opsForValue().set("flower_" + movieid, flower);
            return flower;
        }
    }
    public Map<String, Object> queryPage1(String movieName, String movieClass, String region, String sort, String order, Integer pageNo, Integer pageSize) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        if (!"".equals(movieName) && movieName != null) {
            queryWrapper.like("moviename", movieName);
        }
        if (movieClass != null && !movieClass.isEmpty()) {
            queryWrapper.eq("movieClass", movieClass);
        }
        if (region != null && !region.isEmpty()) {
            queryWrapper.eq("region", region);
        }
        if ("asc".equalsIgnoreCase(order)) {
            queryWrapper.orderByAsc(sort);
        } else {
            queryWrapper.orderByDesc(sort);
        }
        int count = movieMapper.selectCount(queryWrapper).intValue();
        Page<Movie> page = new Page<>(pageNo, pageSize);
        Page<Movie> moviePage = movieMapper.selectPage(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("recourds", page.getRecords());
        return map;
    }
    public List<Movie> searchByKeyword(String keyword) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Movie::getActor, keyword).or().like(Movie::getDirector, keyword);
        return movieMapper.selectList(queryWrapper);
    }

}