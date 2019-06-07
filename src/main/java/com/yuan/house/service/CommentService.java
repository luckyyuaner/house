package com.yuan.house.service;

import com.yuan.house.POJO.CommentPOJO;
import com.yuan.house.model.Comment;

import java.util.List;

public interface CommentService {

    void addCommentByTenant(Comment comment);

    void addCommentByLandlord(Comment comment);

    int deleteComment(Long cid);

    List<CommentPOJO> queryCommentsByHouse(Long hid);
}
