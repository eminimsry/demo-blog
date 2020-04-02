package com.demo.blog.dao;

import com.demo.blog.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagMapper extends JpaRepository<Tag,Long> {

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
