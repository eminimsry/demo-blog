package com.demo.blog.dao;

import com.demo.blog.model.Speak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakMapper extends JpaRepository<Speak,Long> {
}
