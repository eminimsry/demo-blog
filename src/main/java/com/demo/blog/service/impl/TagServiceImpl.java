package com.demo.blog.service.impl;

import com.demo.blog.dao.TagMapper;
import com.demo.blog.dao.TypeMapper;
import com.demo.blog.exception.NotFoundException;
import com.demo.blog.model.Tag;
import com.demo.blog.model.Type;
import com.demo.blog.service.TagService;
import com.demo.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> listTag() {
        return this.tagMapper.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return this.tagMapper.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return this.tagMapper.findAllById(convertToList(ids));
    }

    //将tags(1,2,3,4....)其转换为List<Long>类型的集合
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


}
