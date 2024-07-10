package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.service.MovieService;
import cn.edu.scnu.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController{
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @RequestMapping("/detail")
    public String movieDetail(@RequestParam("movieId") int movieId, Model model, HttpSession session) {
        // 获取当前登录用户
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            // 用户未登录，跳转到登录页面或其他处理方式
            return "redirect:/login";
        }

        // 获取电影信息
        Movie movie = movieService.findMovieByID(String.valueOf(movieId));
        if (movie == null) {
            // 未找到电影，处理方式，比如跳转到错误页面
            return "error";
        }

        // 判断用户是否为VIP以及电影是否需要VIP权限
        boolean canWatch = currentUser.isVip() || !movie.isNeedVip();
        if (canWatch) {
            // 可以观看，跳转至电影详情页面
            model.addAttribute("movie", movie);
            return "movieDetail"; // 替换成你的电影详情页面模板
        } else {
            // 无法观看，跳转至无法观看页面或显示提示信息
            return "cannotWatch"; // 替换成无法观看的页面模板
        }
    }

    @RequestMapping("/index")
    public String index(@RequestParam(name = "pageNo",defaultValue = "1")Integer pageNo,
                        @RequestParam(name = "movename",defaultValue = "")String movieName,
                        Model model){
        Integer pageSize=4;
        Map<String,Object> map=movieService.queryPage(movieName,pageNo,pageSize);
        int totalRecords=(Integer) map.get("count");
        System.out.println(totalRecords);
        List<Movie> movielist=(List<Movie>) map.get("recourds");
        Integer pageCount=(totalRecords%pageSize==0)?(totalRecords/pageSize):((totalRecords/pageSize)+1);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("movies", movielist);
        return "index";
    }
    @RequestMapping("/category")
    public String index(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(name = "movename", defaultValue = "") String movieName,
                        @RequestParam(name = "movieClass", required = false) String movieClass,
                        @RequestParam(name = "region", required = false) String region,
                        @RequestParam(name = "sort", defaultValue = "view") String sort,
                        @RequestParam(name = "order", defaultValue = "asc") String order,
                        Model model) {
        Integer pageSize = 4;
        Map<String, Object> map = movieService.queryPage1(movieName, movieClass, region, sort, order, pageNo, pageSize);
        int totalRecords = (Integer) map.get("count");
        List<Movie> movielist = (List<Movie>) map.get("recourds");
        Integer pageCount = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : ((totalRecords / pageSize) + 1);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("movies", movielist);
        return "category";
    }
    @RequestMapping("/search")
    public String search(@RequestParam(name = "keyword") String keyword, Model model) {
        if (keyword.isEmpty()) {
            return "NosearchResults"; // 如果输入为空，跳转到 "NosearchResults" 页面
        } else {
            List<Movie> results = movieService.searchByKeyword(keyword);
            if (results.isEmpty()) {
                return "NosearchResults"; // 如果搜索结果为空，跳转到 "NosearchResults" 页面
            } else {
                model.addAttribute("movies", results);
                return "searchResults"; // 如果搜索结果不为空，跳转到 "searchResults" 页面
            }
        }
    }

}
