package com.demo.blog.controller.admin;

import com.demo.blog.model.User;
import com.demo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
             HttpSession session,
             RedirectAttributes attributes){
        User user = this.userService.check(username,password);
        if (user == null){
            attributes.addFlashAttribute("errorMessage","用户名或密码错误");
            return "redirect:/admin";
        }else {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }
    }

    @GetMapping("logout")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }




}
