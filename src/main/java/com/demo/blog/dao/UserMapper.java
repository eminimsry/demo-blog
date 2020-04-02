package com.demo.blog.dao;

import com.demo.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapper extends JpaRepository<User,Long> {

   User findByUsernameAndPassword(String username,String password);

   User findByUsername(String username);

}
