package com.demo.blog.controller;

import com.demo.blog.model.Type;
import com.demo.blog.service.BlogService;
import com.demo.blog.service.TypeService;
import com.demo.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TypeShowController {


    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;


    @RequestMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model
    ){
        List<Type> types = typeService.listTypeTop(1000);
        if(id==-1){//直接点击头部的的分类,等同于没有选择type分类,传过来的type_id值默认为-1
            id = types.get(0).getId();//取所有type分类中的第一个(0)type作为默认选中的的type
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);//用于指示,哪个type是选中有颜色的状态
        return "types";



    }


}
