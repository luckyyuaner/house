package com.yuan.house.dao;

import com.yuan.house.model.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeedbackDao {
    void addFeedback(@Param("object") Feedback object);

    List<Feedback> queryFeedbacksByUserId(@Param("uid")Long uid);

    int deleteFeedback(@Param("fid")Long fid);

    int updateAgreeFeedback(@Param("object") Feedback object);
}
