package com.yuan.house.service.impl;

import com.yuan.house.constants.Constants;
import com.yuan.house.dao.FeedbackDao;
import com.yuan.house.model.Feedback;
import com.yuan.house.model.User;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.FeedbackService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private CommonService commonService;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addFeedback(Feedback feedback) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        feedback.setCreateId(user.getUserId());
        feedback.setRoleId(new Long(3));
        feedback.setStatus(0);
        feedback.setCtime(new java.sql.Timestamp(System.currentTimeMillis()));
        feedbackDao.addFeedback(feedback);
    }

    @Override
    public List<Feedback> queryFeedbacksByUserId(Long uid) {
        String key = "feedbacks_user_" + uid;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Feedback>)rs;
        }
        List<Feedback> feedbacks = feedbackDao.queryFeedbacksByUserId(uid);
        commonService.insertRedis(key, feedbacks);
        return feedbacks;
    }
}
