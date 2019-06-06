package com.yuan.house.dao;

import com.yuan.house.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    void addCommentByTenant(@Param("object")Comment object);

    int updateHouseGrade(@Param("cid")Long cid, @Param("grade")double grade, @Param("count")double count);

    int updateLandlordGrade(@Param("cid")Long cid, @Param("grade")double grade, @Param("count")double count, @Param("uid")Long uid);

    List<Comment> queryCommentsByContract(@Param("cid")Long cid);

    int queryHouseCommentCount(@Param("cid")Long cid);

    int queryLandlordCommentCount(@Param("uid")Long uid);

    int deleteComment(@Param("cid")Long cid);
}
