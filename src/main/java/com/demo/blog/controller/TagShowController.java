package com.demo.blog.controller;

import com.demo.blog.model.Tag;
import com.demo.blog.service.BlogService;
import com.demo.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagservice;
    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String Tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Tag> tags = tagservice.listTagTop(1000);
        if(id == -1){//直接点击头部的的分类,等同于没有选择Tag分类,传过来的Tag_id值默认为-1
            id = tags.get(0).getId();//取所有Tag分类中的第一个(0)Tag作为默认选中的的Tag
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);//用于指示,哪个Tag是选中有颜色的状态
        return "tags";
    }
}
