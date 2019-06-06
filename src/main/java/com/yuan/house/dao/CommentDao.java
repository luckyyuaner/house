package com.yuan.house.dao;

import com.yuan.house.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    void addCommentByTenant(@Param("object")Comment object);

    void addCommentByLandlord(@Param("object")Comment object);

    int updateHouseGrade(@Param("cid")Long cid, @Param("grade")double grade, @Param("count")double count);

    int updateLandlordGrade(@Param("grade")double grade, @Param("count")double count, @Param("uid")Long uid);

    int updateTenantGrade(@Param("grade")double grade, @Param("uid")Long uid,  @Param("count")double count);

    List<Comment> queryCommentsByContract(@Param("cid")Long cid);

    int queryHouseCommentCount(@Param("cid")Long cid);

    int queryLandlordCommentCount(@Param("uid")Long uid);

    int queryTenantCommentCount(@Param("uid")Long uid);

    int deleteComment(@Param("cid")Long cid);
}
