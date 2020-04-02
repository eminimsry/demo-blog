package com.demo.blog.controller;

import com.demo.blog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TestController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable Long id,@PathVariable String name){

        System.out.println("------index------");

        return "index";
    }

}
