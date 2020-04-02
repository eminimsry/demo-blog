package com.demo.blog.service;

import com.demo.blog.Utils.MD5Util;
import com.demo.blog.dao.UserMapper;
import com.demo.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User check(String username, String password) {
        User user = this.userMapper.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }

    @Transactional
    public User save(User user){
       user.setPassword(MD5Util.code(user.getPassword()));
       user.setCreateTime(new Date());
       user.setUpdateTime(new Date());
       user.setAvatar("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3420167924,2921767554&fm=26&gp=0.jpg");
       user.setType("路人");
       return this.userMapper.save(user);
    }

    public User findByName(String username){
        return this.userMapper.findByUsername(username);
    }




}
