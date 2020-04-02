package com.demo.blog.service;

import com.demo.blog.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getTypeById(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    Type updateType(Long id,Type type);

    void deleteTypeById(Long id);

    Type getTypeByname(String name);

    List<Type> listTypeTop(Integer size);

}
