package com.demo.blog.dao;

import com.demo.blog.model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeMapper extends JpaRepository<Type,Long> {


    Type getTypeByname(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
