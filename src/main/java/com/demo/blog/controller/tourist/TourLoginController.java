package com.demo.blog.controller.tourist;

import com.demo.blog.model.Speak;
import com.demo.blog.model.User;
import com.demo.blog.service.SpeakService;
import com.demo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tourist")
public class TourLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpeakService speakService;

    @GetMapping
    public String torLogin(){
        return "tourist/login";
   }

   @PostMapping("/login")
   public String login(String username, String password,
                       HttpSession session, RedirectAttributes attributes){
       User check = this.userService.check(username, password);
       if (check == null){
           attributes.addFlashAttribute("errorMessage","未通过身份验证，请尽快离开，否则，呵呵");
           return "redirect:/tourist";
       }else {
           check.setPassword(null);
           session.setAttribute("user",check);
       }
       return "redirect:/";
   }

    @GetMapping("/register")
    public String toRegister() {//跳至游客注册页面
        return "tourist/register";
    }

    @PostMapping("/saveTourist")
    public String register(String username,User user, RedirectAttributes attributes, Model model){
        User u = this.userService.findByName(username);
        if (u!=null){
            model.addAttribute("errorMessage","该用户名已被注册");
            return "tourist/register";
        }else {
            User save = this.userService.save(user);
            if (save==null){
                model.addAttribute("errorMessage","注册失败~");
                return "tourist/register";
            }else {
                attributes.addFlashAttribute("successMessage","注册成功！奈斯~~");
                return "redirect:/tourist";
            }
        }
    }

    @GetMapping("/speak")
    public String toSpeak() {//跳至游客留言页面
        return "tourist/speak-input";
    }

    @PostMapping("/saveSpeak")
    public String saveSpeak(
            @RequestParam("content")String content, HttpSession session,
            RedirectAttributes attributes, Model model
            ){
        User user = (User) session.getAttribute("user");
        if (user == null){
            attributes.addFlashAttribute("errorMessage", "请先登录");
            return "redirect:/tourist";
        }else {
            Speak speak = new Speak();
            speak.setUser(user);
            speak.setContent(content);
            Speak sp = this.speakService.saveSpeak(speak);
            if(sp!=null) {//新增成功
                model.addAttribute("message", "新增成功");
                return "/tourist/speak-input";
            }else {//新增失败
                model.addAttribute("message", "新增失败");
                return "/tourist/speak-input";
            }
        }
    }


}
