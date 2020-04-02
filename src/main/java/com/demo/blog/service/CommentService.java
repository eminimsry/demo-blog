package com.demo.blog.service;

import com.demo.blog.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBid(Long bolgId);

    Comment saveComment(Comment comment);

}
