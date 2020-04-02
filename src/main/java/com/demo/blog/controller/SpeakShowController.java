package com.demo.blog.controller;

import com.demo.blog.service.SpeakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpeakShowController {

    @Autowired
    private SpeakService speakService;

    @GetMapping("speak")
    public String findSpeakNow(Model model){
       model.addAttribute("newspeak",this.speakService.getOneSpeak(new Long(1)));
        return "index";
    }

}
