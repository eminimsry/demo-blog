package com.demo.blog.dao;

import com.demo.blog.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentMapper extends JpaRepository<Comment,Long> {

    //查找所有指定blog_id的且prarent_comment_id为null的所有Comment
//    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    List<Comment> findByBlogIdAndParentCommentNull(Long bolgId, Sort sort);
}
