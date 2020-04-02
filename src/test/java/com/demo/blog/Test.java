package com.demo.blog;

import com.demo.blog.dao.BlogMapper;
import com.demo.blog.dao.TagMapper;
import com.demo.blog.model.Blog;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private BlogMapper blogMapper;



    @org.junit.Test
    public void test(){
        Blog one = this.blogMapper.getOne(new Long(19));
        System.out.println(one);
    }

}
