package com.yuan.house.service;


import com.yuan.house.model.Comment;

public interface CommentService {

    void addCommentByTenant(Comment comment);

    int deleteComment(Long cid);
}
