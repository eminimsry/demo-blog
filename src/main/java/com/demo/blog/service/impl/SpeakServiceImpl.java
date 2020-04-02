package com.demo.blog.service.impl;


import com.demo.blog.dao.SpeakMapper;
import com.demo.blog.model.Speak;
import com.demo.blog.service.SpeakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpeakServiceImpl implements SpeakService {

    @Autowired
    private SpeakMapper speakMapper;

    @Override
    public Speak saveSpeak(Speak speak) {
        speak.setId(null);
        speak.setAgreenum(0);
        speak.setAginstnum(0);
        speak.setCreateTime(new Date());
        return speakMapper.save(speak);
    }

    @Override
    public Speak getOneSpeak(Long id) {
        return this.speakMapper.getOne(id);
    }
}
