package com.yuan.house.service;


import com.yuan.house.model.Feedback;

import java.util.List;

public interface FeedbackService {
    void addFeedback(Feedback feedback);

    List<Feedback> queryFeedbacksByUserId(Long uid);

    int deleteFeedback(Long fid);

    int updateAgreeFeedback(Long fid);
}
