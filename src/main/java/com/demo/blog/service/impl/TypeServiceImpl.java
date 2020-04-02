package com.demo.blog.service.impl;

import com.demo.blog.dao.TypeMapper;
import com.demo.blog.exception.NotFoundException;
import com.demo.blog.model.Type;
import com.demo.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Type saveType(Type type) {
        Type type1 = new Type();
        type1.setName(type.getName());
        return this.typeMapper.save(type1);
    }

    @Override
    public Type getTypeById(Long id) {
        return this.typeMapper.getOne(id);
    }


    @Override
    public Page<Type> listType(Pageable pageable) {
        return this.typeMapper.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return this.typeMapper.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type one = this.typeMapper.getOne(id);
        if (one == null) {
            throw new NotFoundException("不存在");
        }
        BeanUtils.copyProperties(type,one);
      return  this.typeMapper.save(one);
    }

    @Override
    public void deleteTypeById(Long id) {
        this.typeMapper.deleteById(id);
    }

    @Override
    public Type getTypeByname(String name) {
        return this.typeMapper.getTypeByname(name);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeMapper.findTop(pageable);
    }


}
