package com.demo.blog.service.impl;

import com.demo.blog.Utils.MarkdownUtils;
import com.demo.blog.Utils.MyBeanUtils;
import com.demo.blog.dao.BlogMapper;
import com.demo.blog.exception.NotFoundException;
import com.demo.blog.model.Blog;
import com.demo.blog.model.Type;
import com.demo.blog.service.BlogService;
import com.demo.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public Blog getBlog(Long id) {
        return this.blogMapper.getOne(id);
    }

    @Override
    public Blog getBlogConentC(Long id) {
        Blog blog = this.getBlog(id);
        if (blog == null) {
            throw new NotFoundException("博客不存在");
        }
        Blog blog1 = new Blog();
        BeanUtils.copyProperties(blog, blog1);
        blog1.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blogMapper.updateViews(id);
        return blog1;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, final BlogQuery blog) {
        return blogMapper.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();//用来存放条件的容器
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return this.blogMapper.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(final Long tagId, Pageable pageable) {

        return blogMapper.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return this.blogMapper.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return this.blogMapper.findByQuery(query, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return this.blogMapper.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = this.blogMapper.getOne(id);
        if (one == null) {
            throw new NotFoundException("不存在");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return this.blogMapper.save(one);
    }

    @Override
    public void deleteBlog(Long id) {
        this.blogMapper.deleteById(id);
    }

    @Override
    public Map<String, List<Blog>> archivceBlog() {
        List<String> years = this.blogMapper.archiveBlog();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,this.blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return this.blogMapper.count();
    }

}
