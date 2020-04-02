package com.demo.blog.service;

import com.demo.blog.model.Speak;

public interface SpeakService {

    Speak saveSpeak(Speak speak);

   Speak getOneSpeak(Long id);

}
