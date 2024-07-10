package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/top-rated")
    @ResponseBody
    public List<Movie> getTopRatedMovies() {
        return movieService.getTopRatedMovies();
    }

    @GetMapping("/top-rated-view")
    public String getTopRatedMoviesView(Model model) {
        model.addAttribute("movies", movieService.getTopRatedMovies());
        return "top-rated"; // 确保返回的视图名称与模板文件名称一致
    }
    @GetMapping("/movieDetail/{movieid}")
    public String MovieDetails (@PathVariable("movieid")Integer movieid, Model model) {
        Movie movie = movieService.findMovieByID(movieid.toString());
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "movieDetail"; // 返回电影详情页面的视图名
        } else {
            // 处理电影不存在的情况，可以跳转到错误页面或其他逻辑
            return "error"; // 这里简单地返回一个错误页面
        }
    }
}

