package com.yuan.house.dao;

import com.yuan.house.model.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentDao {
    void addCommentByTenant(@Param("object")Comment object);

    int updateHouseGrade(@Param("cid")Long cid, @Param("grade")double grade);

    int updateLandlordGrade(@Param("cid")Long cid, @Param("grade")double grade);
}
