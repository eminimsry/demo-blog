package com.demo.blog.controller;

import com.demo.blog.Utils.APIUtil;
import com.demo.blog.Utils.IpUtil;
import com.demo.blog.service.BlogService;
import com.demo.blog.service.TagService;
import com.demo.blog.service.TypeService;
import com.demo.blog.vo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", this.blogService.listBlog(pageable));
        model.addAttribute("types", this.typeService.listTypeTop(6));
        model.addAttribute("tags", this.tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", this.blogService.listBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query); //将查询的字符串回显
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", this.blogService.getBlogConentC(id));
        return "blog";
    }


    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listBlogTop(3));
        return "_fragments :: newblogList";
    }

    @GetMapping("/getWeather")
    public String test(HttpServletRequest request, Model model){
        //获取IP地址
        String ipAddress = IpUtil.getIpAddr(request);
        System.out.println(ipAddress);
        if(ipAddress.equals("0:0:0:0:0:0:0:1")) {
//        	1.192.119.149  鄭州
//        	223.104.212.171 上海
//        	42.227.160.255 南陽
            ipAddress = "223.104.212.171";
        }
        Weather weather = APIUtil.returnWeather(ipAddress);

        if(weather!=null&&weather.getCity().contains("未知")) {//今日接口次数使用已达上线
            model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message","今日请求次数已达上限");
        }else if(weather!=null&&weather.getCity().contains("获取失败")) {//HttpGetPost请求超时(Time Out)
            model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message","请求超时");
        }else{//请求正常
            model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message",null);
        }

        return "index :: weatherCard";
    }


}
